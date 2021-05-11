package ua.des.kino.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.des.kino.model.mysql.Cinema;

@org.springframework.stereotype.Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Cinema findByName(String name);

}
