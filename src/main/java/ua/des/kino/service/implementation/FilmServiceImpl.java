package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Film;
import ua.des.kino.repository.FilmRepository;
import ua.des.kino.service.FilmService;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    public static final Logger logger = LoggerFactory.getLogger(FilmServiceImpl.class.getName());

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    @Transactional
    public Film getById(Long id) {
        return filmRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException("Film with id: " + id + " isn't exist.", new Throwable()));
    }

    @Override
    @Transactional
    public boolean isExist(Film film) {
        return filmRepository.existsByName(film.getName());
    }

    @Override
    @Transactional
    public List<Film> getFilmByName(String name) {
        return filmRepository.findByNameLike(name);
    }

    @Override
    @Transactional
    public Long save(Film film){
        Film movie = filmRepository.save(film);
        return movie.getId();
    }

    @Override
    @Transactional
    public List<Film> getFilmList() {
        return filmRepository.findAll();
    }

    @Override
    @Transactional
    public void update(Film film){
        filmRepository.saveAndFlush(film);
    }

    @Override
    @Transactional
    public void delete(Long id){
    filmRepository.deleteById(id);
    }

}
