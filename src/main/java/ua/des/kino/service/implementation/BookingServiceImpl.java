package ua.des.kino.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Booking;
import ua.des.kino.model.Ticket;
import ua.des.kino.model.User;
import ua.des.kino.repository.BookingRepository;
import ua.des.kino.service.BookingService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;

    public BookingServiceImpl(BookingRepository repository) {
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
    public Long saveBooking(Booking booking) {
        return repository.save(booking).getId();
    }

    @Override
    @Transactional
    public Set<Ticket> buyTicket(Booking booking) {
        if (booking.getPay()) {
            return repository.saveAndFlush(booking).getTicket();
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
        if (bookingList.contains(booking)) {
            repository.delete(booking);
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
            if(booking.getCreateDate().isBefore(limitTime)){
                repository.delete(booking);
            } else {
                filtered.add(booking);
            }
        });
        return filtered;
    }
}
