package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.Booking;
import ua.des.kino.model.Ticket;
import ua.des.kino.model.User;

import java.util.List;
import java.util.Set;

@Service
public interface BookingService {

    List<Booking> findBookingListByUser(Long id);

    void cancelBooking(Long userId, Booking booking);

    Long saveBooking(Booking booking);

    Set<Ticket> buyTicket(Booking booking);


}
