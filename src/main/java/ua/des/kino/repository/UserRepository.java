package ua.des.kino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLoginAndPassword(String login, String password);

    Optional<User> getUserByLogin(String login);
}
