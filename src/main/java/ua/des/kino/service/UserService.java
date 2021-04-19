package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.User;

import java.util.List;

@Service
public interface UserService {

    User getByLogin(String login);

    Boolean isUserExist(User user);

    User getById(Long id);

    List<User> findAll();

    void save(User user);

    void updateUser(User user);

    void deleteUser(Long id);
}
