package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.audience.Booking;
import ua.des.kino.model.audience.Customer;

import java.util.List;

@Service
public interface CustomerService {

    Customer getByLogin(String login);

    Boolean isUserExist(Long id);

    Customer getById(Long id);

    List<Customer> findAll();

    Customer save(Customer customer);

    void updateUser(Customer customer);

    void deleteUser(Long id);

    List<Booking> bookingListByCustomer(Long id);

    Booking toBookTickets(Booking booking);

    Booking buyTickets(Booking booking);

    void cancelBooking(Long userId, Booking booking);
}
