package ua.des.kino.daos.repository.user;

import org.springframework.stereotype.Repository;
import ua.des.kino.model.User;

import java.util.List;

@Repository
public interface UserRepositoryCustom {

    List<User> getAllUsersData();

    User getUserData(Long id);

    User findByEntityGraph(Long id);

    User findUserByJPQL(Long id);

    User findById(Long id);

    void remove(Long id);

    void save(User user);
}
