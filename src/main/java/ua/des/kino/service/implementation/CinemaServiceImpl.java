package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.mysql.Cinema;
import ua.des.kino.model.mysql.Room;
import ua.des.kino.repository.mysql.CinemaRepository;
import ua.des.kino.repository.mysql.RoomRepository;
import ua.des.kino.service.CinemaService;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {

    public static final Logger logger = LoggerFactory.getLogger(CinemaServiceImpl.class.getName());

    private final CinemaRepository cinemaRepository;
    private final RoomRepository roomRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository, RoomRepository roomRepository) {
        this.cinemaRepository = cinemaRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public boolean isExists(Long id) {
        return cinemaRepository.existsById(id);
    }

    @Override
    @Transactional
    public Cinema findById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException("Cinema with id: " + id + " isn't exist.", new Throwable()));
    }

    @Override
    @Transactional
    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema findByName(String name) {
        return cinemaRepository.findByName(name);
    }

    @Override
    public List<Room> getCinemaInfo(Long id) {
        Cinema cinema = findById(id);
        Collection<Room> rooms = roomRepository.findByCinema_Id(id);
        if (rooms.isEmpty()) {
            throw new NoSuchElementFoundException("Cinema with id: " + id + " hasn't rooms.", new Throwable());
        }
        return rooms.stream()
                .peek(room -> room.setCinema(cinema))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long save(Cinema cinema) {
        logger.info("Create: " + cinema.toString());
        Cinema c = cinemaRepository.save(cinema);
        return c.getId();
    }

    @Override
    @Transactional
    public Cinema update(Cinema cinema) {
        return cinemaRepository.saveAndFlush(cinema);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cinemaRepository.deleteById(id);
    }
}
