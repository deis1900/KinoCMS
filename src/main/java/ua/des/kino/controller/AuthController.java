package ua.des.kino.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.audience.User;
import ua.des.kino.service.UserService;
import ua.des.kino.util.UserValidation;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Auth_Controller", description = "Authentication.")
public class AuthController {

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class.getName());

    private final UserValidation userValidator;

    public AuthController(UserService userService, UserValidation userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @Operation(summary = "login enter",
            description = "login")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> login(@Valid @RequestBody User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        User userFromDB = userService.findByLogin(user.getLogin());
        if (user.getPassword().equals(userFromDB.getPassword())) {
            LOGGER.info("User " + user.getUsername() + " is logged in.");
            LOGGER.info("Password user is " + user.getPassword());
            LOGGER.info("User has role as " + userFromDB.getAuthorities());
            userService.setUserToken(userFromDB);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>(userFromDB, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Operation(summary = "logout",
            description = "logout. Set current token deprecated.")
    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout() {
        User user = userService.findByLogin("anonymous");
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("User had logout.");
            return new ResponseEntity<>("User had logout.", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "create new user",
            description = "create new user and get token")
    @PostMapping(value = "/registration")
    public ResponseEntity<String> registerForm(@RequestBody User userForm, BindingResult bindingResult) {

        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        LOGGER.info(userForm.getUsername() + " : " + userForm.getPassword());

        userService.save(userForm);
        LOGGER.info("User - " + userForm.getUsername() + " - is saved.");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            LOGGER.info("User " + userForm.getUsername() + " is logged in.");
            userService.setUserToken(userForm);
            LOGGER.info(userForm.getToken());
            return new ResponseEntity<>(userForm.getToken(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Not logged", HttpStatus.NOT_ACCEPTABLE);
    }
}

