package ua.des.kino.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.des.kino.model.audience.User;
import ua.des.kino.service.UserService;

@Component
public class UserValidation implements Validator {

    private final UserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserValidation.class);

    public UserValidation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        if (user.getUsername().length() < 3 || user.getUsername().length() > 32) {
            LOGGER.info("Login length is wrong.");
            errors.rejectValue("login", "User login from userForm have incorrect size.");
        }
        if (userService.findByLogin(user.getLogin()) == null) {
            LOGGER.info("Not find user with login " + user.getLogin());
            errors.rejectValue("login", "User with "+ user.getLogin() + " not exists.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 7 || user.getPassword().length() > 32) {
            LOGGER.info("Password length is wrong");
            errors.rejectValue("password", "Size.userForm.password");
        }
    }
}
