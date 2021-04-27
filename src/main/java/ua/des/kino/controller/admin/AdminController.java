package ua.des.kino.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.submodel.Photo;
import ua.des.kino.service.PhotoService;
import ua.des.kino.util.CustomErrorType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@Tag(name = "Admin_Controller", description = "Manage other options for admin panel.")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class.getName());

    @Value("${upload.path}")
    private String uploadPath;

    private final PhotoService photoService;

    public AdminController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Operation(
            summary = "get photo by id",
            description = "find photo by id of the photo"
    )
    @GetMapping(value = "/photo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Photo> getPhotoById(@PathVariable Long id) {
        return new ResponseEntity<>(photoService.getPhotoById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "get photo by name and type of Page",
            description = "Find photo for page and name of the photo"
    )
    @GetMapping(value = {"/get/photo/{name}", "/get/photo/byType"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPhotoByNameAndType(
            @RequestParam(required = false) String type,
            @PathVariable(required = false) String name) {

        if (type == null && name == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (type == null) {
            return new ResponseEntity<>(photoService.getPhotoByName(name), HttpStatus.OK);
        } else if (name == null) {
            return new ResponseEntity<>(photoService.getPhotoByType(type), HttpStatus.OK);
        }
        return new ResponseEntity<>(photoService.getPhotoByNameAndType(name, type), HttpStatus.OK);
    }

    @Operation(
            summary = "save photo",
            description = "Downloading, convert and save to database Photo files."
    )
    @PostMapping(value = "/photo")
    public ResponseEntity<?> savePhoto(@Valid @RequestBody
                                       @Parameter(description = "Generated photo.") Photo photo) {
        logger.info("Creating photo " + photo.getUrl());
        if (photoService.isExists(photo)) {
            logger.error("Photo already exist " + photo.getUrl());
            return new ResponseEntity<>(
                    new CustomErrorType("Photo with name: " + photo.getName() +
                            " `" + photo.getUrl() + "` already exist!"),
                    HttpStatus.CONFLICT);
        }
        photoService.save(photo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // TODO Check request!
    @Operation(
            summary = "update photo",
            description = "Downloading, convert and update to database Photo files."
    )
    @PutMapping(value = "/photo/{id}")
    public ResponseEntity<?> updatePhoto(@PathVariable("id") @Parameter(description = "Valid ID from the DB") Long id,
                                         @Valid @RequestBody
                                         @Parameter(description ="Corrected photo to the DB.") Photo photo) {

        logger.info("Update photo with id " + id);
        Photo ph = photoService.getPhotoById(id);

        if (ph == null) {
            logger.error("Unable to update. Photo with id '" + id + "' not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (photo.equals(photoService.getPhotoById(photo.getId()))) {
            logger.error("A Photo with " + photo.getId() + " already exist ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        photoService.update(photo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete photo by Id",
            description = "delete photo By id"
    )
    @DeleteMapping(value = "photo/{id}")
    public ResponseEntity<?> deletePhotoById(@PathVariable @Parameter(description = "Valid ID from the DB") Long id) {
        logger.info("Fetching & Deleting Photo with id " + id);
        Photo photo = photoService.getPhotoById(id);
        if (photo == null) {
            logger.error("Unable to delete. Photo with id '" + id + "' not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        photoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "delete photos by name",
            description = "delete list photo by name from the database."
    )
    @DeleteMapping(value = "photo/by")
    public ResponseEntity<?> deleteAllPhotoByName(@RequestParam @NotEmpty String name) {
        logger.info("Fetching & Deleting photos with name " + name);
        List<Photo> photoList = photoService.getPhotoByName(name);
        if (photoList.isEmpty()) {
            logger.error("Unable to delete. Photo with name '" + name + "' not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        photoService.deleteAllByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}