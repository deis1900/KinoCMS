package ua.des.kino.controller.admin;

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
import ua.des.kino.model.mysql.Session;
import ua.des.kino.model.postgres.Ticket;
import ua.des.kino.service.TicketService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "admin/ticket")
@Tag(name = "Ticket_Controller", description = "Communicate with tickets(only for administrator)")
public class TicketController {

    private final Logger logger = LoggerFactory.getLogger(TicketController.class.getName());

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "get Ticket by id",
            description = "get ticket by id")
    @JsonView(Views.Custom.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTicketByID(@PathVariable("id")
                                           @Parameter(description = "Descriptor ticket") Long id) {
        logger.info("Get ticket with id: " + id);
        return new ResponseEntity<>(ticketService.getById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "get Ticket list"
    )
    @JsonView(Views.Custom.class)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> getListTickets() {

        return new ResponseEntity<>(ticketService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "get Session Tickets",
            description = "getting a seats in a specific session"
    )
    @JsonView(Views.Custom.class)
    @GetMapping(value = "/session",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSessionTickets(@Valid @RequestBody Session session) {
        logger.info(session.toString());
        if(session.getFilm().getQuality() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticketService.getSessionTickets(session), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete ticket",
            description = "Delete ticket from database by id."
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable("id") @Parameter(description = "id of ticket") Long id) {
        logger.info("Fetching & Deleting Ticket with id " + id);

        Ticket ticket = ticketService.getById(id);
        if (ticket == null) {
            logger.error("Unable to delete. Ticket with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ticketService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
