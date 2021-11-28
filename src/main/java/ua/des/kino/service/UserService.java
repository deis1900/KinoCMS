package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.postgres.Booking;
import ua.des.kino.model.postgres.User;

import java.util.List;

@Service
public interface UserService {

    User getByLogin(String login);

    Boolean isUserExist(Long id);

    User getById(Long id);

    List<User> findAll();

    User save(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    List<Booking> bookingListByUser(Long id);

    Booking toBookTickets(Booking booking);

    Booking buyTickets(Booking booking);

    void cancelBooking(Long userId, Booking booking);
}
