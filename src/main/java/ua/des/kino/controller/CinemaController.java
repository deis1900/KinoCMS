package ua.des.kino.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.config.Views;
import ua.des.kino.model.Cinema;
import ua.des.kino.model.Room;
import ua.des.kino.service.CinemaService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cinema")
@Tag(name = "Cinema_Controller", description = "Communicate with cinema (all changes  only for administrator)")
public class CinemaController {

    private final Logger logger = LoggerFactory.getLogger(CinemaController.class.getName());

    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Operation(summary = "get Cinema by id",
            description = "Find all info about cinema by id, else throw NoSuchElementException"
    )
    @JsonView(Views.Public.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Room>> getCinemaById(@PathVariable("id")
                                             @Parameter(description = "ID of cinema") Long id){
        logger.info("Get Cinema with id: " + id);
        return new ResponseEntity<>(cinemaService.getCinemaInfo(id), HttpStatus.OK);
    }

    @Operation(summary = "get list of Cinema",
            description = "get list of Cinema or empty list"
    )
    @JsonView(Views.Internal.class)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cinema>> getListOfCinema(){
        return new ResponseEntity<>(cinemaService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "save new Cinema",
            description = "save new Cinema"
    )
    @JsonView(Views.Internal.class)
    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveRoom(@Valid @RequestBody
                                      @Parameter(description = "Generated cinema") Cinema cinema) {
        Cinema cinemaDB = cinemaService.findByName(cinema.getName());
        if (cinemaDB == null) {
            logger.info("Save new cinema: " + cinema.toString());
            return new ResponseEntity<>(cinemaService.save(cinema), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(cinema, HttpStatus.CONFLICT);

    }

    @Operation(summary = "Update Cinema",
            description = "Update Cinema.Entity if those exists, else create new Cinema."
    )
    @PutMapping(value = "/admin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @Valid @RequestBody Cinema cinema) {

        logger.info("Update cinema with id " + id);
        Cinema cinemaDB = cinemaService.findById(id);

        if (cinemaDB == null) {
            logger.error("Unable to update. Cinema with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (cinema.equals(cinemaDB)) {
            logger.error("Cinema with " + cinema.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        cinema.setId(id);
        return new ResponseEntity<>(cinemaService.update(cinema), HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete cinema",
            description = "Delete cinema from database by id."
    )
    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<?> deleteSessions(@PathVariable("id") @Parameter(description = "id of room") Long id) {

        logger.info("Fetching & Deleting Cinema with id " + id);
        Cinema cinemaDB = cinemaService.findById(id);

        if (cinemaDB == null) {
            logger.error("Unable to delete. Room with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cinemaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
