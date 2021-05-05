package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Session;
import ua.des.kino.model.Ticket;
import ua.des.kino.model.submodel.Quality;
import ua.des.kino.repository.BookingRepository;
import ua.des.kino.repository.TicketRepository;
import ua.des.kino.service.TicketService;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class.getName());

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, BookingRepository bookingRepository) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
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
        return ticketRepository.save(ticket);
    }

    //    TODO add Exception handler
    @Override
    @Transactional
    public List<Ticket> getSessionTickets(Session session) {
        List<Ticket> tickets = ticketRepository.findTicketBySession_Id(session.getId());
        Quality filmQuality = session.getFilm().getFilmDetails().getQuality();
        int price = session.getTicket().iterator().next().getPrice();
        if (price == 0) {
            price = 80;
        }
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
            default:
                break;
        }
        logger.info("Change price to " + price);
        int finalPrice = price;
        return tickets.stream().peek(t -> t.setPrice(finalPrice)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
