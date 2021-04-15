package ua.des.kino.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.User;
import ua.des.kino.repository.UserRepository;
import ua.des.kino.util.exception_handler.session.NoSuchElementFoundException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User getByLogin(String login) {
        return repository.getUserByLogin(login).orElseThrow(() ->
                new NoSuchElementFoundException("User with login " + login + " is not found", new Throwable()));
    }

    @Override
    @Transactional
    public Boolean isUserExist(User user) {
        return repository.existsById(user.getId());
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return repository.getOne(id);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        repository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

    }
}
