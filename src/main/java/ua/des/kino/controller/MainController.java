package ua.des.kino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.des.kino.model.Film;
import ua.des.kino.service.FilmService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private static final Logger logger =
            LoggerFactory.getLogger(MainController.class.getName());

    @Autowired
    private FilmService filmService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("index", HttpStatus.OK);
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/home")
    public ResponseEntity<List<Film>> main(@RequestParam(required = false, defaultValue = "") String filter) {
        if (filter != null && !filter.isEmpty()) {
            List<Film> films = new ArrayList<>();
            var iterFilm = filmService.findByTitleLike(filter);
            //TODO Type's choose

           return new ResponseEntity<>(films, HttpStatus.OK);
        }
           return new ResponseEntity<>(filmService.getFilmList(), HttpStatus.NOT_FOUND);

    }

//    @PostMapping("/main")
//    public String add(
//            @AuthenticationPrincipal User user,
//            @RequestParam String text,
//            @RequestParam String tag, Map<String, Object> model,
//            @RequestParam("file") MultipartFile file
//    ) throws IOException {
//        Message message = new Message(text, tag, user);
//
//        if (file != null && !file.getOriginalFilename().isEmpty()) {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + "." + file.getOriginalFilename();
//
//            file.transferTo(new File(uploadPath + "/" + resultFilename));
//
//            message.setFilename(resultFilename);
//        }
//
//        filmRepository.save(message);
//
//        Iterable<Message> messages = filmRepository.findAll();
//
//        model.put("messages", messages);
//
//        return "main";
//    }
}
