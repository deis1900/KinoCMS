package ua.des.kino.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
@Tag(name="Main", description="Give user HTML page.")
public class MainController {

    private static final Logger logger =
            LoggerFactory.getLogger(MainController.class.getName());

    @Operation(
            summary = "Get index.html.",
            description = "The method allows the user to load the main page."
    )
    @GetMapping(value = "/index")
    public String index() {
        return "index.html";
    }
}
