package ua.des.kino.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Booking;
import ua.des.kino.model.User;
import ua.des.kino.model.submodel.UserDetails;
import ua.des.kino.repository.UserRepository;
import ua.des.kino.service.BookingService;
import ua.des.kino.service.UserService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.EntityIdMismatchException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BookingService bookingService;

    public UserServiceImpl(UserRepository repository, BookingService bookingService) {
        this.repository = repository;
        this.bookingService = bookingService;
    }

    @Override
    @Transactional
    public User getByLogin(String login) {
        return repository.findUserByLogin(login).orElseThrow(() ->
                new NoSuchElementFoundException("User with login " + login + " is not found.", new Throwable()));
    }

    @Override
    @Transactional
    public Boolean isUserExist(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityIdMismatchException("User with id " + id + " isn't exist. ", new Throwable()));
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        UserDetails details = user.getDetails();
        details.setRegistrationDate(LocalDateTime.now());
        user.setDetails(details);
        return repository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        repository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Booking toBookTickets(Booking booking) {
        if(isUserExist(booking.getUser().getId())){
            return bookingService.saveBooking(booking);
        }
        throw new EntityDataException("User with id: " + booking.getUser().getId() + " isn't exist", new Throwable());
    }

    @Override
    public Booking buyTickets(Booking booking) {
        return bookingService.buyTicket(booking);
    }

    @Override
    public List<Booking> bookingListByUser(Long id) {
        return bookingService.findBookingListByUser(id);
    }

    @Override
    public void cancelBooking(Long userId, Booking booking) {
        if(userId.equals(booking.getUser().getId())) {
            bookingService.cancelBooking(userId, booking);
        } else throw new EntityIdMismatchException("User with id: " + userId + "." +
                " Cannot cancel booking another user.", new Throwable());
    }

}
