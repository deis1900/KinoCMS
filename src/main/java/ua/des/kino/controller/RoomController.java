package ua.des.kino.controller;

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
import ua.des.kino.model.mysql.Room;
import ua.des.kino.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "{cinema}/room")
public class RoomController {

    private final Logger logger = LoggerFactory.getLogger(RoomController.class.getName());

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "get room by id",
            description = "get Room by id, else throw NoSuchElementException"
    )
    @JsonView(Views.Internal.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoom(@PathVariable("id")
                                         @Parameter(description = "ID of room") Long id) {

        return new ResponseEntity<>(roomService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "get list of room",
            description = "get list of room or empty list"
    )
    @JsonView(Views.Internal.class)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Room>> getRoomList(@PathVariable String cinema) {
        return new ResponseEntity<>(roomService.findAllByCinemaName(cinema), HttpStatus.OK);
    }

    @Operation(summary = " update room",
            description = "Update Room.Entity if those exists, else create new room."
    )
    @PutMapping(value = "/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @Valid @RequestBody Room room) {
        logger.info("Update room with id " + id);
        Room roomDB = roomService.findById(id);

        if (roomDB == null) {
            logger.error("Unable to update. Room with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (room.equals(roomDB)) {
            logger.error("Room with " + room.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        room.setId(id);
        return new ResponseEntity<>(roomService.update(room), HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete room",
            description = "Delete room from database by id."
    )
    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") @Parameter(description = "id of room") Long id) {
        logger.info("Fetching & Deleting Room with id " + id);
        Room roomDB = roomService.findById(id);
        if (roomDB == null) {
            logger.error("Unable to delete. Room with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roomService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
