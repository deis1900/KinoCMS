package ua.des.kino.repository.kino;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.kino.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Cinema findByName(String name);

}
