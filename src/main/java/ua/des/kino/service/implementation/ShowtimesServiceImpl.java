package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Film;
import ua.des.kino.model.Session;
import ua.des.kino.repository.ShowtimeRepository;
import ua.des.kino.service.FilmService;
import ua.des.kino.service.ShowtimesService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.EntityIdMismatchException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShowtimesServiceImpl implements ShowtimesService {

    public static final Logger logger = LoggerFactory.getLogger(ShowtimesServiceImpl.class.getName());

    private final ShowtimeRepository showtimeRepository;
    private final FilmService filmService;

    public ShowtimesServiceImpl(ShowtimeRepository showtimeRepository, FilmService filmService) {
        this.showtimeRepository = showtimeRepository;
        this.filmService = filmService;
    }

    @Override
    public Session findById(Long id) {
        return showtimeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException("Session with id " + id + " isn't exist.", new Throwable()));
    }

    @Override
    @Transactional
    public List<Session> getCurrentFilms(LocalDateTime start) {
        return showtimeRepository.findSessionsByShowTimeAfterOrderByShowTime(start);
    }

    @Override
    @Transactional
    public List<Session> findSessionsOnMonth(LocalDateTime start) {
        var end = start.plusDays(30);
        return showtimeRepository.findSessionsByShowTimeBetweenOrderByShowTimeAsc(start, end);
    }

    @Override
    @Transactional
    public List<Session> finAllSessions() {
        return showtimeRepository.findAll();
    }

    @Override
    @Transactional
    public List<Session> saveAll(Set<Session> sessionsIn) {

        // TODO rewrite & create Exception handlers
        List<Long> ids = sessionsIn.stream()
                .map(Session::getId)
                .collect(Collectors.toList());
        List<Session> sessionsDB = showtimeRepository.findBySessionIds(ids);
        sessionsDB.forEach(System.out::println);

        List<Session> duplicates = new ArrayList<>();
        for (Session session : sessionsIn) {
            if(reconcileDates(session)) {

                duplicates = sessionsDB.stream()
                        .filter(session::equals)
                        .filter(value -> session.getShowTime().equals(value.getShowTime()))
                        .filter(value -> session.getRoom().equals(value.getRoom()))
                        .collect(Collectors.toList());
            }

        }
//        sessionsIn(duplicates);
        showtimeRepository.saveAll(sessionsIn);
        return duplicates;
    }

    @Override
    @Transactional
    public Session update(Session session) {
        return showtimeRepository.saveAndFlush(session);
    }

    @Override
    public void delete(Long id) {
        showtimeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean reconcileDates(Session session) {
        Film filmDB;
        try {
            filmDB = filmService.getById(session.getFilm().getId());
        } catch (Exception e) {
            logger.error("Film with id " + session.getFilm().getId() + " (from set session) is not exist.");
            throw new EntityIdMismatchException("Film from this session is not exist.", e);
        }
        LocalDate finishFilmShow = session.getFilm().getFinishDate();

        if (filmDB.getFinishDate().isEqual(finishFilmShow)) {
            return session.getShowTime().isBefore(ChronoLocalDateTime.from(finishFilmShow));

        } else throw new EntityDataException("This Film show is over." + session.getShowTime() +
                " : " + finishFilmShow, new Throwable());
    }
}

