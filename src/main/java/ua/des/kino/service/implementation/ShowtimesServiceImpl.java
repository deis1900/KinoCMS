package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.mysql.Film;
import ua.des.kino.model.mysql.Room;
import ua.des.kino.model.mysql.Session;
import ua.des.kino.repository.mysql.ShowtimeRepository;
import ua.des.kino.service.FilmService;
import ua.des.kino.service.RoomService;
import ua.des.kino.service.ShowtimesService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShowtimesServiceImpl implements ShowtimesService {

    public static final Logger logger = LoggerFactory.getLogger(ShowtimesServiceImpl.class.getName());

    private final ShowtimeRepository showtimeRepository;
    private final FilmService filmService;
    private final RoomService roomService;

    public ShowtimesServiceImpl(ShowtimeRepository showtimeRepository,
                                FilmService filmService,
                                RoomService roomService) {
        this.showtimeRepository = showtimeRepository;
        this.filmService = filmService;
        this.roomService = roomService;
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
    public Session update(Session session) {
        return showtimeRepository.saveAndFlush(session);
    }

    @Override
    public void delete(Long id) {
        showtimeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Session> saveAll(Set<Session> sessionsIn) {
        List<Session> unique = checkInputDate(sessionsIn);

        if (!unique.isEmpty()) {
            showtimeRepository.saveAll(unique);
            return sessionsIn.stream()
                    .filter(unique::contains)
                    .collect(Collectors.toList());
        }
        throw new EntityDataException("Room or Film isn't exists", new Throwable());
    }

    private List<Session> checkInputDate(Set<Session> sessionsIn) {
        List<Film> dbFilms = filmService.getFilmList();
        List<Room> dbRooms = roomService.findAll();
        List<Session> dbSessions = showtimeRepository.findAll();

        List<Long> filmIds = dbFilms.stream()
                .map(Film::getId)
                .collect(Collectors.toList());

        List<Long> roomIds = dbRooms.stream()
                .map(Room::getId)
                .collect(Collectors.toList());

        return sessionsIn.stream()
                .filter(value -> reconcileDates(value, dbFilms))
                .filter(value -> filmIds.contains(value.getFilm().getId()))
                .filter(value -> roomIds.contains(value.getRoom().getId()))
                .filter(value -> isExistRoom(value, dbRooms))
                .filter(value -> busyPlaceAndTime(value, dbSessions))
                .collect(Collectors.toList());
    }


    private boolean reconcileDates(Session session, List<Film> films) {
        for (Film film : films) {
            if (film.getId().equals(session.getFilm().getId())) {
                session.setFilm(film);
            }
        }

        if (session.getFilm().getName().equals("")) {
            throw new NoSuchElementFoundException("Film isn't exist.", new Throwable());
        }

        LocalDate finishDateOfFilm = session.getFilm().getFinishDate();
        LocalDate startDateOfFilm = session.getFilm().getStartDate();
        LocalDate showDate = session.getShowTime().toLocalDate();

        if (session.getShowTime().isAfter(LocalDateTime.now())) {
            if (showDate.isBefore(finishDateOfFilm)) {
                return showDate.isAfter(startDateOfFilm);
            } else
                throw new EntityDataException("Session has wrong show date." + session.getShowTime(), new Throwable());
        } else
            throw new EntityDataException("Cannot set show date of session ("
                    + session.getShowTime() + ") in the past.", new Throwable());
    }

    private boolean isExistRoom(Session session, List<Room> rooms) {
        for (Room room : rooms) {
            if (room.getId().equals(session.getRoom().getId())) {
                session.setRoom(room);
                return true;
            }
        }

        if (session.getRoom().getName().equals("")) {
            throw new NoSuchElementFoundException("Room isn't exist.", new Throwable());
        }
        return false;
    }

    private boolean busyPlaceAndTime(Session session, List<Session> sessions) {
        for (Session s : sessions) {
            if (s.getRoom().getId().equals(session.getRoom().getId())) {
                if (s.getShowTime().equals(session.getShowTime())) {
                    throw new EntityDataException(
                            "The room - " + session.getRoom().getName() +
                                    " in the cinema -" + session.getRoom().getCinema().getName() +
                                    " is busy at " + session.getShowTime() + ".",
                            new Throwable());
                }
//                TODO before film not finished new wouldn't start
//                long filmDuration = s.getFilm().getDuration().getLong();
//                long timeoutBetweenShowtimes = Duration.between(s.getShowTime(), session.getShowTime()).getSeconds();
//                if ( timeoutBetweenShowtimes > filmDuration){
//                    throw new EntityDataException(
//                            "New session (" + session.getShowTime() + ")" +
//                            " cannot begin before previous film (" + s.getShowTime() +
//                            " duration " + s.getFilm().getDuration() + ") stopped.",
//                            new Throwable());
//                }
            }
        }
        return true;
    }

}

