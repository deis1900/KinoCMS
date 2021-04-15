package ua.des.kino.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Film;
import ua.des.kino.repository.FilmRepository;
import ua.des.kino.util.convertor.TimeConvertor;
import ua.des.kino.util.exception_handler.session.NoSuchElementFoundException;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository repository;
    TimeConvertor convertor= new TimeConvertor();

    public FilmServiceImpl(FilmRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Film getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException("Film with id: " + id, new Throwable()));
    }

    @Override
    @Transactional
    public List<Film> getFilmByName(String name) {
        return repository.findByNameLike(name);
    }

    @Override
    @Transactional
    public List<Film> getCurrentFilms(String start) {
        List<Timestamp> dates = convertor.convertToTimestamp(new String[]{start});
        ZonedDateTime zonedDateTime = dates.get(0).toInstant().atZone(ZoneId.of("UTC"));
        Timestamp finishDate = Timestamp.from(zonedDateTime.plus(30, ChronoUnit.DAYS).toInstant());
        return repository.getFilmsByStartDateAfterAndFinishDateBefore(dates.get(0), finishDate);
    }

    @Override
    @Transactional
    public List<Film> getFilmsByDates(String start, String finish) {
        List<Timestamp> dates = convertor.convertToTimestamp(new String[]{start, finish});
        return repository.getFilmsByStartDateAfterAndFinishDateBefore(dates.get(0), dates.get(1));
    }

    @Override
    @Transactional
    public List<Film> getFilmList() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Iterable<Film> findByTitleLike(String filter) {
        return repository.findByNameLike(filter);
    }

}
