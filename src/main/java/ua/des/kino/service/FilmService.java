package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.mysql.Film;

import java.util.List;


@Service
public interface FilmService {

    Film getById(Long id);

    boolean isExist(Film film);

    List<Film> getFilmByName(String name);

    Long save(Film film);

    void update(Film film);

    void delete(Long id);

    List<Film> getFilmList();
}
