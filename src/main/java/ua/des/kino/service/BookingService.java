package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.audience.Booking;

import java.util.List;

@Service
public interface BookingService {

    List<Booking> findBookingListByCustomer(Long id);

    void cancelBooking(Long userId, Booking booking);

    Booking saveBooking(Booking booking);

    Booking buyTicket(Booking booking);

}
