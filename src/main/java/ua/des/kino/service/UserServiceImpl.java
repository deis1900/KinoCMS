package ua.des.kino.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.daos.repository.user.UserRepositoryCustom;
import ua.des.kino.model.User;
import ua.des.kino.daos.repository.user.UserRepository;
import ua.des.kino.util.exception_handler.EntityIdMismatchException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
//    private final UserRepositoryCustom customRepository;

    public UserServiceImpl(UserRepository repository
//            , @Qualifier("userRepositoryCustomImpl") UserRepositoryCustom customRepository
    ) {
//        this.customRepository = customRepository;
        this.repository = repository;
    }

    @Override
    @Transactional
    public User getByLogin(String login) {
        return repository.findUserByLogin(login).orElseThrow(() ->
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
        return repository.findById(id).orElseThrow( () ->
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

}
