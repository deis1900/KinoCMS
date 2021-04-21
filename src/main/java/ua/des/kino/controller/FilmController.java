package ua.des.kino.controller;

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
import ua.des.kino.model.Film;
import ua.des.kino.model.Session;
import ua.des.kino.service.FilmService;
import ua.des.kino.service.SessionService;
import ua.des.kino.util.CustomErrorType;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "film")
@Tag(name="Film_Controller", description="Communicate with films.")
public class FilmController {

    private static final Logger logger =
            LoggerFactory.getLogger(FilmController.class.getName());

    private final FilmService filmService;
    private final SessionService sessionService;

    public FilmController(FilmService filmService, SessionService sessionService) {
        this.filmService = filmService;
        this.sessionService = sessionService;
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
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postCustomer(@Valid @RequestBody
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
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody Film film) {

        logger.info("Update film with id " + id);
        Film filmDB = filmService.getById(id);

        if (filmDB == null) {
            logger.error("Unable to update. Film with id '" + id + "' not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (film.equals(filmService.getById(film.getId()))) {
            logger.error("A Film with " + film.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        filmService.update(film);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete film",
            description = "Delete Film from database by id."
    )
    @DeleteMapping(value = "/{id}")
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
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Session>> getCurrentFilms(@RequestParam
                                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Parameter(description = "start date for sessions movies")
                                                             final LocalDateTime start) {
        logger.info(start.toString());
        return new ResponseEntity<>(sessionService.getCurrentFilms(start), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Showtimes sorted by date",
            description = "Schedule of sessions for a month and sort by date."
    )
    @GetMapping(value = "/showtimes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Session>> getShowtimeOnMonth(@RequestParam
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                                final LocalDateTime start){
        return new ResponseEntity<>(sessionService.findSessionsOnMonth(start), HttpStatus.OK);
    }
}
