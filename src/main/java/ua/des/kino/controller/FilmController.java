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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        System.out.println("Fetching film with id " + id);
        Film film = filmService.getById(id);
        return new ResponseEntity<>(film, HttpStatus.OK);
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
            summary = "get films from the period",
            description = "."
    )
    @GetMapping(value = "/byDates")
    public ResponseEntity<?> getFilmsByDates(@RequestParam(required = false, defaultValue = "")
                                                         Map<String, String> mapVars) {
        String begin = mapVars.get("start");
        String end = mapVars.get("finish");
        List<Film> films = filmService.getFilmsByDates(begin, end);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @Operation(
            summary = "get current film",
            description = "Show customers movie sessions on today."
    )
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Session>> getCurrentFilms(@RequestParam
                                                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Parameter(description = "start date for sessions movies")
                                                             final LocalDateTime start) {
        logger.info(start.toString());
        return new ResponseEntity<>(sessionService.getCurrentFilms(start), HttpStatus.OK);
    }
}
