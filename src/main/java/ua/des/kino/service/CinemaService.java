package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.kino.Cinema;
import ua.des.kino.model.kino.Room;

import java.util.List;

@Service
public interface CinemaService {

    Long save(Cinema cinema);

    Cinema update(Cinema cinema);

    Cinema findById(Long id);

    boolean isExists(Long id);

    void delete(Long id);

    List<Cinema> findAll();

    Cinema findByName(String name);

    List<Room> getCinemaInfo(Long id);
}
