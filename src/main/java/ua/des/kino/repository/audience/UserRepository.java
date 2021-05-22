package ua.des.kino.repository.audience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.audience.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    List<User> findByAccountNonLockedLike(Boolean lockVar);

    Optional<User> findUserByLogin(String login);

}
