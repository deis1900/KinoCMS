package ua.des.kino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.User;
import ua.des.kino.service.UserService;
import ua.des.kino.util.CustomErrorType;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(String login) {
        return new ResponseEntity<>(userService.getByLogin(login), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/ ", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postCustomer(@RequestBody User user) {
        System.out.println("Creating User " + user.getLogin());
        if (userService.isUserExist(user)) {
            logger.error("login already exist " + user.getLogin());
            return new ResponseEntity<>(
                    new CustomErrorType("user with login " + user.getLogin() + " already exist!"),
                    HttpStatus.CONFLICT);
        }
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody User user) {

        logger.info("Update user with id " + id);
        User userDB = userService.getById(id);

        if (userDB == null) {
            logger.error("Unable to update. User with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user.equals(userService.getById(user.getId()))) {
            System.out.println("A User with " + user.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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
