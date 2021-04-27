package ua.des.kino.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.Cinema;
import ua.des.kino.service.CinemaService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/cinema")
public class CinemaController {

    private final Logger logger = LoggerFactory.getLogger(CinemaController.class.getName());

    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Operation(summary = "get Cinema by id",
            description = "get Cinema by id, else throw NoSuchElementException"
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCinemaById(@PathVariable("id")
                                           @Parameter(description = "ID for cinema") Long id){
        return new ResponseEntity<>(cinemaService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "get list of Cinema",
            description = "get list of Cinema or empty list"
    )
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cinema>> getListOfCinema(){
        return new ResponseEntity<>(cinemaService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "save new Cinema",
            description = "save new Cinema"
    )
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveRoom(@Valid @RequestBody
                                      @Parameter(description = "Generated cinema") Cinema cinema) {

        Cinema cinemaDB = cinemaService.findByName(cinema.getName());
        if (cinemaDB.equals(cinema)) {
            return new ResponseEntity<>(cinema, HttpStatus.CONFLICT);
        }
        logger.info("Save new cinema: " + cinema.toString());
        return new ResponseEntity<>(cinemaService.save(cinema), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Cinema",
            description = "Update Cinema.Entity if those exists, else create new Cinema."
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @Valid @RequestBody Cinema cinema) {

        logger.info("Update cinema with id " + id);
        Cinema cinemaDB = cinemaService.findById(id);

        if (cinemaDB == null) {
            logger.error("Unable to update. Cinema with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (cinema.equals(cinemaService.findById(id))) {
            logger.error("Cinema with " + cinema.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(cinemaService.update(cinema), HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete cinema",
            description = "Delete cinema from database by id."
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSessions(@PathVariable("id") @Parameter(description = "id of room") Long id) {

        logger.info("Fetching & Deleting Cinema with id " + id);
        Cinema cinemaDB = cinemaService.findById(id);

        if (cinemaDB == null) {
            logger.error("Unable to delete. Room with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cinemaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
