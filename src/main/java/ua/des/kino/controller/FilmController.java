package ua.des.kino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.Film;
import ua.des.kino.service.FilmService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "film")
public class FilmController {

    private static final Logger logger =
            LoggerFactory.getLogger(FilmController.class.getName());

    private final FilmService service;

    public FilmController(FilmService filmService) {
        this.service = filmService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Film>> getAllFilm(){
        List<Film> films = service.getFilmList();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Film> getUser(@PathVariable("id") Long id) {
        System.out.println("Fetching film with id " + id);
        Film film = service.getById(id);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping(value = "/byName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Film>> getFilmByName(@PathVariable String name){
        return new ResponseEntity<>(service.getFilmByName(name), HttpStatus.OK);}

    @GetMapping(value = "/byDates")
    public ResponseEntity<?> getFilmsByDates(@RequestParam(required = false, defaultValue = "") Map<String, String> mapVars){
        String begin = mapVars.get("start");
        String end = mapVars.get("finish");
        List<Film> films = service.getFilmsByDates(begin, end);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Film>> getCurrentFilms(@RequestParam String start){
        return new ResponseEntity<>(service.getCurrentFilms(start), HttpStatus.OK);
    }
}
