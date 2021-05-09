package ua.des.kino.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Booking;
import ua.des.kino.model.Ticket;
import ua.des.kino.repository.BookingRepository;
import ua.des.kino.service.BookingService;
import ua.des.kino.service.TicketService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.EntityIdMismatchException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final TicketService ticketService;
    private final BookingRepository repository;

    public BookingServiceImpl(BookingRepository repository, TicketService ticketService) {
        this.ticketService = ticketService;
        this.repository = repository;
    }

    @Override
    public List<Booking> findBookingListByUser(Long id) {
        List<Booking> bookingList = repository.findAllByUser_Id(id);
        if (bookingList.isEmpty()) {
            throw new NoSuchElementFoundException("User with id:" + id + " has no booking.", new Throwable());
        }
        return cleanOldBooking(bookingList);
    }

    @Override
    @Transactional
    public Booking saveBooking(Booking booking) {
        Set<Ticket> inTickets = booking.getTickets();
        if (inTickets.isEmpty()) {
            throw new EntityDataException("Booking has empty set of tickets", new Throwable());
        }
        Set<Ticket> ticketSet = inTickets.stream()
                .map(ticketService::verifyTicket)
                .collect(Collectors.toSet());

        booking.setTickets(ticketSet);
        booking.setCreateDate(LocalDateTime.now());
        booking.setPay(false);
        return repository.save(booking);
    }

    @Override
    @Transactional
    public Booking buyTicket(Booking booking) {
// All tickets free )))
        Booking bookingDB = getBookingById(booking.getId());
        if (bookingDB.equals(booking)) {
            throw new EntityIdMismatchException("Booking id: " +
                    booking.getId() + " is not exists in Database.", new Throwable());
        }
        if (booking.getPay()) {
            return repository.saveAndFlush(booking);
        }

        throw new EntityDataException("Payment has not been confirmed. The ticket was not purchased.",
                new Throwable());
    }

    @Override
    @Transactional
    public void cancelBooking(Long userId, Booking booking) {

        List<Booking> bookingList = repository.findAllByUser_Id(userId);
        if (bookingList.isEmpty()) {
            throw new NoSuchElementFoundException("User with id:" + userId + " has no booking.", new Throwable());
        }
        var list = bookingList.stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .filter(b -> b.getUser().getId().equals(booking.getUser().getId()))
                .filter(b -> b.getCreateDate().equals(booking.getCreateDate()))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            repository.deleteById(booking.getId());
        } else {
            throw new NoSuchElementFoundException(booking.toString() + " not found.", new Throwable());
        }
    }

    /**
     * if the date of creation of the booking
     * has passed more than 2 days,
     * then the booking  is canceled
     */
    List<Booking> cleanOldBooking(List<Booking> bookingList) {
        LocalDateTime limitTime = LocalDateTime.now().minusDays(2L);
        List<Booking> filtered = new ArrayList<>();

        bookingList.forEach(booking -> {
            if (booking.getCreateDate().isBefore(limitTime)) {
                repository.delete(booking);
            } else {
                filtered.add(booking);
            }
        });
        return filtered;
    }

    private Booking getBookingById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementFoundException("Cannot buy ticket, because booking with id:"
                                + id + " isn't exists.", new Throwable()));
    }
}
