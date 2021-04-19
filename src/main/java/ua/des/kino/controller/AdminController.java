package ua.des.kino.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.submodel.Photo;
import ua.des.kino.service.util.PhotoService;

@RestController
@RequestMapping(value = "/admin")
@Tag(name="Admin_Controller", description="Manage other options for admin panel.")
public class AdminController {

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
    public ResponseEntity<Photo> getPhotoByNameAndType(@PathVariable Long id) {
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
            description = "Downloading, convert snd save to database Photo files."
    )
    @PostMapping(value = "/save/photo")
    public ResponseEntity<?> savePhoto(Photo photo) {
        photoService.save(photo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
