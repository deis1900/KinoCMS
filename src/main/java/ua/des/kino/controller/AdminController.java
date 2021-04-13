package ua.des.kino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.submodel.Photo;
import ua.des.kino.service.util.PhotoService;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final PhotoService photoService;

    public AdminController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(value = "/photo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Photo> getPhotoByNameAndType(@PathVariable Long id) {
        return new ResponseEntity<>(photoService.getPhotoById(id), HttpStatus.OK);
    }

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

    @PostMapping(value = "/save/photo")
    public ResponseEntity<?> savePhoto(Photo photo) {
        photoService.save(photo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
