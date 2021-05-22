package ua.des.kino.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.audience.Booking;
import ua.des.kino.model.audience.Customer;
import ua.des.kino.model.audience.submodel.CustomerDetails;
import ua.des.kino.repository.audience.CustomerRepository;
import ua.des.kino.service.BookingService;
import ua.des.kino.service.CustomerService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.EntityIdMismatchException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final BookingService bookingService;

    public CustomerServiceImpl(CustomerRepository repository, BookingService bookingService) {
        this.repository = repository;
        this.bookingService = bookingService;
    }

    @Override
    @Transactional
    public Customer getByLogin(String login) {
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
    public Customer getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityIdMismatchException("User with id " + id + " isn't exist. ", new Throwable()));
    }

    @Override
    @Transactional
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        CustomerDetails details = customer.getDetails();
        details.setRegistrationDate(LocalDateTime.now());
        customer.setDetails(details);
        return repository.save(customer);
    }

    @Override
    @Transactional
    public void updateUser(Customer customer) {
        repository.saveAndFlush(customer);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Booking toBookTickets(Booking booking) {
        if(isUserExist(booking.getCustomer().getId())){
            return bookingService.saveBooking(booking);
        }
        throw new EntityDataException("User with id: " + booking.getCustomer().getId() + " isn't exist", new Throwable());
    }

    @Override
    public Booking buyTickets(Booking booking) {
        return bookingService.buyTicket(booking);
    }

    @Override
    public List<Booking> bookingListByCustomer(Long id) {
        return bookingService.findBookingListByCustomer(id);
    }

    @Override
    public void cancelBooking(Long userId, Booking booking) {
        if(userId.equals(booking.getCustomer().getId())) {
            bookingService.cancelBooking(userId, booking);
        } else throw new EntityIdMismatchException("User with id: " + userId + "." +
                " Cannot cancel booking another user.", new Throwable());
    }

}
