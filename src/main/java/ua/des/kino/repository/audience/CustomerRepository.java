package ua.des.kino.repository.audience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.audience.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findUserByLogin(String login);

    Optional<Customer> findCustomerByContact_Email(String email);

}
