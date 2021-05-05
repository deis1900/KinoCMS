package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.Booking;
import ua.des.kino.model.Ticket;
import ua.des.kino.model.User;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {

    User getByLogin(String login);

    Boolean isUserExist(User user);

    User getById(Long id);

    List<User> findAll();

    User save(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    List<Booking> bookingListByUser(Long id);

    Long toBookTickets(Booking booking);

    Set<Ticket> buyTickets(Booking booking);

    void cancelBooking(Long userId, Booking booking);
}
