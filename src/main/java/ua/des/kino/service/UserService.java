package ua.des.kino.service;

import ua.des.kino.model.audience.User;

import java.util.List;

public interface UserService  {

    List<User> findByUserWhereAccountNonLocked(Boolean varLock);

    List<User> findAll();

    void save(User user);

    User findByEmail(String email);

    User findByLogin(String Login);

    String setUserToken(User user);
}
