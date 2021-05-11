package ua.des.kino.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ua.des.kino.model.postgres.User;

import java.util.List;
import java.util.Optional;

@Repository
// TODO create initialization
@Sql(scripts = {"/data-postgres.sql"},
        config = @SqlConfig(encoding = "utf-8",
                dataSource = "postgresDataSource",
                transactionMode = SqlConfig.TransactionMode.ISOLATED))
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLoginAndPassword(String login, String password);

    Optional<User> findUserByLogin(String login);

}
