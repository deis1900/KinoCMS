package ua.des.kino.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.User;
import ua.des.kino.service.UserService;
import ua.des.kino.util.CustomErrorType;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "user")
@Tag(name="User_Controller", description="Communicate with customers.")
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
        return new ResponseEntity<>(
                userService.getById(id),
                HttpStatus.OK);
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
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<?> postCustomer(@Valid @RequestBody
                                                      @Parameter(description = "Generated user.") User user) {
        logger.info("Creating User " + user.getLogin());
        if (userService.isUserExist(user)) {
            logger.error("login already exist " + user.getLogin());
            return new ResponseEntity<>(
                    new CustomErrorType("user with login " + user.getLogin() + " already exist!"),
                    HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @Operation(
            summary = "update customer",
            description = "Save and flush user to db."
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody User user) {

        logger.info("Update user with id " + id);
        User userDB = userService.getById(id);

        if (userDB == null) {
            logger.error("Unable to update. User with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user.equals(userService.getById(user.getId()))) {
            logger.error("A User with " + user.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete user",
            description = "Delete Customer from database by id."
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteCustomer(@PathVariable("id") Long id) {
        logger.info("Fetching & Deleting User with id " + id);
        User customer = userService.getById(id);
        if (customer == null) {
            logger.error("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
