package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Session;
import ua.des.kino.model.Ticket;
import ua.des.kino.model.submodel.Quality;
import ua.des.kino.repository.TicketRepository;
import ua.des.kino.service.ShowtimesService;
import ua.des.kino.service.TicketService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private List<Session> sessions = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class.getName());

    private final TicketRepository ticketRepository;
    private final ShowtimesService showtimesService;

    public TicketServiceImpl(TicketRepository ticketRepository, ShowtimesService showtimesService) {
        this.ticketRepository = ticketRepository;
        this.showtimesService = showtimesService;
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementFoundException("Ticket with id: " + id + " isn't exist", new Throwable()));
    }

    @Override
    public boolean isExists(Long id) {
        return ticketRepository.existsById(id);
    }

    @Override
    @Transactional
    public Ticket saveTicket(Ticket ticket) {
        if(sessions.isEmpty()){
            sessions = showtimesService.finAllSessions();
        }
        return ticketRepository.save(verifyTicket(ticket));
    }

    @Override
    @Transactional
    public List<Ticket> getSessionTickets(Session session) {
        List<Ticket> tickets = ticketRepository.findAllBySession_Id(session.getId());
        if(tickets.isEmpty()){
            throw new EntityDataException("Session with id: " + session.getId() +" hasn't tickets.",
                    new Throwable());
        }

        List<Ticket> t = tickets.stream()
                .peek(ticket -> ticket.setPrice(verifyPrice(session)))
                .collect(Collectors.toList());

        if(t.isEmpty()){
            throw new EntityDataException("Tickets was empty", new Throwable());
        }
        return t;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Ticket verifyTicket(Ticket ticket) {
        if(sessions.isEmpty()){
            sessions = showtimesService.finAllSessions();
        }
        Session session =  sessions.stream()
                .filter(s -> ticket.getSession().getId().equals(s.getId()))
                .findAny()
                .orElseThrow(() -> new EntityDataException("Session isn't exist.", new Throwable()));
        Integer price = verifyPrice(ticket.getSession());

        if(verifySeat(ticket)) {
            ticket.setPrice(price);
            ticket.setSession(session);
            return ticket;
        } throw new EntityDataException("The seat " + ticket.getSeat().toString() + " was taken", new Throwable());
    }

//    TODO Exclude a possibility of substitution of the price from frontend
    private Integer verifyPrice(Session session){
        Quality filmQuality = session.getFilm().getQuality();
        int price = 80;

        switch (filmQuality) {
            case DD:
                price = price * 2;
                break;
            case DDD:
                price = price * 3;
                break;
            case IMAX:
                price = price * 4;
                break;
        }
        logger.info("Change price to " + price);
        return price;
    }

    // TODO insert verify seats
    private Boolean verifySeat(Ticket ticket){
        return true;
    }
}
