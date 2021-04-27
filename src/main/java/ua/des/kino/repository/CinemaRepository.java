package ua.des.kino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.des.kino.model.Cinema;

@org.springframework.stereotype.Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Cinema findByName(String name);

}
