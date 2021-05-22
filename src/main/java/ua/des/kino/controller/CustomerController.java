package ua.des.kino.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.config.Views;
import ua.des.kino.model.audience.Booking;
import ua.des.kino.model.audience.Customer;
import ua.des.kino.service.CustomerService;
import ua.des.kino.util.CustomErrorType;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/customer")
@Tag(name = "Customer_Controller", description = "Communicate with customers.")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class.getName());

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
// TODO check Login (User.login equals Customer.login)
    @Operation(
            summary = "get user by id",
            description = "."
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getUserById(@PathVariable("id")
                                            @Parameter(description = "Descriptor user") Long id) {
        logger.info("Get user with id: " + id);
        return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "get user by login",
            description = "."
    )
    @GetMapping(value = "/name/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getUserByLogin(@PathVariable
                                               @Parameter(description = "User login.") String login) {
        return new ResponseEntity<>(customerService.getByLogin(login), HttpStatus.OK);
    }

    @Operation(
            summary = "get all customer",
            description = "."
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAllUsers() {
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Operation(
            summary = "save customer",
            description = "."
    )
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postUser(@Valid @RequestBody
                                      @Parameter(description = "Generated user.") Customer customer) {
        logger.info("Creating User " + customer.getLogin());
        if (customerService.isUserExist(customer.getId())) {
            logger.error("Login already exist " + customer.getLogin());
            return new ResponseEntity<>(
                    new CustomErrorType("User with login " + customer.getLogin() +
                            " already exist and the same!"),
                    HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @Operation(
            summary = "update customer",
            description = "Save and flush user to db."
    )
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody Customer customer) {

        logger.info("Update user with id " + id);
        Customer customerDB = customerService.getById(id);

        if (customerDB == null) {
            logger.error("Unable to update. User with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (customer.equals(customerDB)) {
            logger.error("A User with " + customer.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        customer.setId(id);
        customerService.updateUser(customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete user",
            description = "Delete Customer from database by id."
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteUser(@PathVariable("id") Long id) {
        logger.info("Fetching & Deleting User with id " + id);
        Customer customer = customerService.getById(id);
        if (customer == null) {
            logger.error("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "get all booking user",
            description = "get all user bookings and delete all outdated ones."
    )
    @JsonView(Views.Custom.class)
    @GetMapping(value = "/booking/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> getBookingList(@PathVariable("id") Long id) {
        return new ResponseEntity<>(customerService.bookingListByCustomer(id), HttpStatus.OK);
    }

    @Operation(
            summary = "create booking",
            description = "create booking or buy ticket"
    )
    @JsonView(Views.Custom.class)
    @PostMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> toBookTickets(@Valid @RequestBody
                                              @Parameter(description = "Save booking") Booking booking) {
        return new ResponseEntity<>(customerService.toBookTickets(booking), HttpStatus.CREATED);
    }

    @Operation(
            summary = "buy ticket",
            description = "buy ticket without PaymentServices. Only pay parameter should change to true"
    )
    @JsonView(Views.Custom.class)
    @PutMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> buyTickets(@Valid @RequestBody
                                                          @Parameter(description = "Booking entity for update DB")
                                                          Booking booking) {

        return new ResponseEntity<>(customerService.buyTickets(booking), HttpStatus.OK);
    }

//  not safety! should rewrite! frontend can set id other user

    @Operation(
            summary = "delete booking by user id",
            description = "canceled booking by user id parameter"
    )
    @JsonView(Views.Public.class)
    @DeleteMapping(value = "/booking/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable("id") Long userId,
                                           @Valid @RequestBody
                                           @Parameter( description = "canceled booking")
                                                   Booking booking){
        customerService.cancelBooking(userId, booking);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
