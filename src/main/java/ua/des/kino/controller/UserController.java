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
import ua.des.kino.model.Booking;
import ua.des.kino.model.Ticket;
import ua.des.kino.model.User;
import ua.des.kino.service.UserService;
import ua.des.kino.util.CustomErrorType;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "User_Controller", description = "Communicate with customers.")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "get user by id",
            description = "."
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id")
                                            @Parameter(description = "Descriptor user") Long id) {
        logger.info("Get user with id: " + id);
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "get user by login",
            description = "."
    )
    @GetMapping(value = "/name/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByLogin(@PathVariable
                                               @Parameter(description = "User login.") String login) {
        return new ResponseEntity<>(userService.getByLogin(login), HttpStatus.OK);
    }

    @Operation(
            summary = "get all customer",
            description = "."
    )
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(
            summary = "save customer",
            description = "."
    )
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postUser(@Valid @RequestBody
                                      @Parameter(description = "Generated user.") User user) {
        logger.info("Creating User " + user.getLogin());
        if (userService.isUserExist(user.getId())) {
            logger.error("Login already exist " + user.getLogin());
            return new ResponseEntity<>(
                    new CustomErrorType("User with login " + user.getLogin() +
                            " already exist and the same!"),
                    HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @Operation(
            summary = "update customer",
            description = "Save and flush user to db."
    )
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {

        logger.info("Update user with id " + id);
        User userDB = userService.getById(id);

        if (userDB == null) {
            logger.error("Unable to update. User with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user.equals(userDB)) {
            logger.error("A User with " + user.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setId(id);
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete user",
            description = "Delete Customer from database by id."
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        logger.info("Fetching & Deleting User with id " + id);
        User customer = userService.getById(id);
        if (customer == null) {
            logger.error("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "get all booking user",
            description = "get all user bookings and delete all outdated ones."
    )
    @JsonView(Views.Custom.class)
    @GetMapping(value = "/booking/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> getBookingList(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.bookingListByUser(id), HttpStatus.OK);
    }

    @Operation(
            summary = "create booking",
            description = "create booking or buy ticket"
    )
    @JsonView(Views.Custom.class)
    @PostMapping(value = "/booking/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> toBookTickets(@Valid @RequestBody
                                              @Parameter(description = "Save booking") Booking booking) {
        return new ResponseEntity<>(userService.toBookTickets(booking), HttpStatus.CREATED);
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

        return new ResponseEntity<>(userService.buyTickets(booking), HttpStatus.OK);
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
        userService.cancelBooking(userId, booking);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
