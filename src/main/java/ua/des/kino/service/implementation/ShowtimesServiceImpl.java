package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.audience.Film;
import ua.des.kino.model.audience.submodel.Seat;
import ua.des.kino.model.kino.Room;
import ua.des.kino.model.audience.Session;
import ua.des.kino.repository.audience.ShowtimeRepository;
import ua.des.kino.service.FilmService;
import ua.des.kino.service.RoomService;
import ua.des.kino.service.ShowtimesService;
import ua.des.kino.util.exception_handler.EntityDataException;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShowtimesServiceImpl implements ShowtimesService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ShowtimesServiceImpl.class.getName());

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
                .filter(value -> roomIds.contains(value.getRoomId()))
                .filter(value -> isExistRoom(value, dbRooms))
                .filter(value -> checkRoomAndDateOfSession(value, dbSessions))
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
            if (room.getId().equals(session.getRoomId())) {
                session.setRoomId(room.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public Session verifySessionAndSeat(Long sessionId, Seat seat) {
        Optional<Session> sessionDB = showtimeRepository.findById(sessionId);
        if (sessionDB.isPresent()) {
            if (checkSeatOccupied(sessionDB.get(), seat)) {
                return sessionDB.get();
            }
        }
        throw new EntityDataException("Cannot booking ticket (Session with id " + sessionId + " isn't exists).",
                new Throwable());

    }

    private boolean checkSeatOccupied(Session session, Seat seat) {
        return roomService.verifySeat(seat, session.getRoomId());
    }

    private boolean checkRoomAndDateOfSession(Session session, List<Session> sessions) {
        var startNewShowtime = session.getShowTime();
        var endNewShowtime = findEndShowtime(startNewShowtime, session.getFilm().getDuration());

        List<Session> sessions1 = sessions.stream()
                .filter(s -> s.getRoomId().equals(session.getRoomId()))
                .filter(s -> s.getShowTime().equals(session.getShowTime()))
                .filter(s -> {
                            var start = s.getShowTime();
                            var end = findEndShowtime(start, s.getFilm().getDuration());
                            var less = end.isBefore(startNewShowtime);
                            var after = start.isAfter(endNewShowtime);
                            return less && after;
                        }
                ).collect(Collectors.toList());

        LOGGER.info("There is a show in the room(new session between "
                + startNewShowtime.toString() + " ; " + endNewShowtime.toString());
        return sessions1.isEmpty();
    }

    LocalDateTime findEndShowtime(LocalDateTime start, LocalTime duration) {
        long durationMinutes = duration.getMinute();
        long durationHours = duration.getHour();

        var end = start.plusHours(durationHours);
        return end.plusMinutes(durationMinutes);
    }

}

