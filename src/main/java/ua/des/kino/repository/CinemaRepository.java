package ua.des.kino.repository;

import org.springframework.data.repository.Repository;
import ua.des.kino.model.Cinema;

@org.springframework.stereotype.Repository
public interface CinemaRepository extends Repository<Cinema, Long> {


}
