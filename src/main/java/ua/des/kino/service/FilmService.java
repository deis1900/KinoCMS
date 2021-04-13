package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.Film;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface FilmService {

    Film getById(Long id);

    List<Film> getFilmByName(String name);

    List<Film> getCurrentFilms(String startDate);

    List<Film> getFilmsByDates(String startDate, String finishDate);

    List<Film> getFilmList();

    Iterable<Film> findByTitleLike(String filter);


}
