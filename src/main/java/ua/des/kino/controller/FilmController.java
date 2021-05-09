package ua.des.kino.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.config.Views;
import ua.des.kino.model.Film;
import ua.des.kino.model.Session;
import ua.des.kino.service.FilmService;
import ua.des.kino.service.ShowtimesService;
import ua.des.kino.util.CustomErrorType;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "film")
@Tag(name = "Film_Controller", description = "Communicate with films(all changes only for administrator)")
public class FilmController {

    private static final Logger logger =
            LoggerFactory.getLogger(FilmController.class.getName());

    private final FilmService filmService;
    private final ShowtimesService showtimesService;

    public FilmController(FilmService filmService, ShowtimesService showtimesService) {
        this.filmService = filmService;
        this.showtimesService = showtimesService;
    }

    @Operation(
            summary = "get all films",
            description = "."
    )
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Film>> getAllFilm() {
        List<Film> films = filmService.getFilmList();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @Operation(
            summary = "get film by id",
            description = "."
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Film> getFilm(@PathVariable("id") Long id) {
        logger.info("Fetching film with id " + id);
        return new ResponseEntity<>(filmService.getById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "get film by name",
            description = "."
    )
    @GetMapping(value = "/byName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Film>> getFilmByName(@PathVariable String name) {
        return new ResponseEntity<>(filmService.getFilmByName(name), HttpStatus.OK);
    }

    @Operation(
            summary = "save new Film",
            description = "."
    )
    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveFilm(@Valid @RequestBody
                                          @Parameter(description = "Generated film.") Film film) {
        logger.info("Creating film " + film.getName());
        if (filmService.isExist(film)) {
            logger.error("Film already exist " + film.getName());
            return new ResponseEntity<>(
                    new CustomErrorType("Film with name '" + film.getName() + "' already exist!"),
                    HttpStatus.CONFLICT);
        }
        Long id = filmService.save(film);
        film.setId(id);
        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    @Operation(
            summary = "update film",
            description = "Save and flush film to db."
    )
    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<?> updateFilm(@PathVariable("id") Long id, @Valid @RequestBody Film film) {

        logger.info("Update film with id " + id);
        Film filmDB = filmService.getById(id);

        if (filmDB == null) {
            logger.error("Unable to update. Film with id '" + id + "' not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (film.equals(filmDB)) {
            logger.error("A Film with " + film.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        film.setId(id);
        filmService.update(film);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete film",
            description = "Delete Film from database by id."
    )
    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
        logger.info("Fetching & Deleting Film with id " + id);
        Film film = filmService.getById(id);
        if (film == null) {
            logger.error("Unable to delete. Film with id '" + id + "' not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        filmService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "get current film",
            description = "Show customers movie sessions on today."
    )
    @JsonView(Views.Public.class)
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Session>> getCurrentFilms(@RequestParam
                                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                         @Parameter(description = "start date for sessions movies")
                                                             final LocalDateTime start) {
        logger.info(start.toString());
        return new ResponseEntity<>(showtimesService.getCurrentFilms(start), HttpStatus.OK);
    }

    @Operation(
            summary = "Get showtimes sorted by date",
            description = "Schedule of sessions for a month and sort by date."
    )
    @JsonView(Views.Public.class)
    @GetMapping(value = "/showtimes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShowtimeOnMonth(@RequestParam
                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                    final LocalDateTime start) {
        logger.info(start.toString());
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(start)) {
            return new ResponseEntity<>("Now " + now + " which is later than " + start, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(showtimesService.findSessionsOnMonth(start), HttpStatus.OK);
    }
}
