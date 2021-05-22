package ua.des.kino.service.implementation;

import ua.des.kino.model.audience.Customer;
import ua.des.kino.model.audience.User;
import ua.des.kino.model.audience.submodel.Authority;
import ua.des.kino.model.audience.submodel.Role;
import ua.des.kino.repository.audience.CustomerRepository;
import ua.des.kino.repository.audience.UserRepository;
import ua.des.kino.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void save(User user) {
        Authority authority = new Authority();
        authority.setRole(Role.USER);
        user.setAuthorities(new ArrayList<>(Collections.singleton(authority)));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        LOGGER.info(user.toString());
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public List<User> findByUserWhereAccountNonLocked(Boolean lockVariable) {
        return userRepository.findByAccountNonLockedLike(lockVariable);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByEmail(String email) {

        Customer customer = customerRepository.findCustomerByContact_Email(email).orElseThrow(() ->
                new NoSuchElementFoundException("Customer with email" + email + " isn't exists",
                        new Throwable()));

        return userRepository.findUserByLogin(customer.getLogin()).orElseThrow(() ->
                new UsernameNotFoundException("Customer hasn't registration. " +
                        "(Row about user with " + customer.getLogin() + " not exists."));

    }

    @Override
    @Transactional
    public User findByLogin(@NotNull String login) {
        return userRepository.findUserByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User with" + login + " was not found. "));
    }

    @Override
    @Transactional
    public String setUserToken(User user) {
        user.setToken(
//                bCryptPasswordEncoder.encode(
                user.getUsername() + ":" + user.getPassword()
//        )
        );
        String token = user.getToken();
        userRepository.saveAndFlush(user);
        return token;
    }
}
