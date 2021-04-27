package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Cinema;
import ua.des.kino.repository.CinemaRepository;
import ua.des.kino.service.CinemaService;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {

    public static final Logger logger = LoggerFactory.getLogger(ShowtimesServiceImpl.class.getName());

    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
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
