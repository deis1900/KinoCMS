package ua.des.kino.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.config.Views;
import ua.des.kino.model.Session;
import ua.des.kino.service.ShowtimesService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "admin/session")
public class ShowtimesController {

    private final Logger logger = LoggerFactory.getLogger(ShowtimesController.class.getName());

    private final ShowtimesService showtimesService;

    public ShowtimesController(ShowtimesService showtimesService) {
        this.showtimesService = showtimesService;
    }

    @Operation(
            summary = "send Session list"
    )
    @JsonView(Views.Public.class)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Session>> getListShowtimes() {

        return new ResponseEntity<>(showtimesService.finAllSessions(), HttpStatus.OK);
    }

    @Operation(
            summary = "save showtimes(Session list)",
            description = "Will save sessions, but if session is exist than response will " +
                    "list of session witch cannot saved."
    )
    @JsonView(Views.Public.class)
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveListSessions(@Valid
                                              @RequestBody
                                              @Parameter(description = "Generated sessions.") Set<Session> sessions) {

        List<Session> duplicates = showtimesService.saveAll(sessions);
        if(duplicates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(duplicates, HttpStatus.CONFLICT);
    }

    @Operation(
            summary = "update session",
            description = "Save and flush session to db."
    )
    @JsonView(Views.Public.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSessions(@PathVariable("id") Long id, @Valid @RequestBody Session session) {
        logger.info("Update session with id " + id);
        Session sessionDB = showtimesService.findById(id);

        if (sessionDB == null) {
            logger.error("Unable to update. Session with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (session.equals(sessionDB)) {
            logger.error("Session with " + session.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(showtimesService.update(session), HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete session",
            description = "Delete session from database by id."
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSessions(@PathVariable("id") @Parameter(description = "id of session") Long id) {
        logger.info("Fetching & Deleting Session with id " + id);
        Session session = showtimesService.findById(id);
        if (session == null) {
            logger.error("Unable to delete. Session with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        showtimesService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
