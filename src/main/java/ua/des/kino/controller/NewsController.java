package ua.des.kino.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.des.kino.model.News;
import ua.des.kino.service.NewsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/news")
@Tag(name = "News_Controller", description = "Communicate with news(all changes only for administrator)")
public class NewsController {

    private final Logger logger = LoggerFactory.getLogger(NewsController.class.getName());
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Operation(summary = "gives a list of news for 3 months ahead",
            description = "gives a list of news for 3 months ahead, date and time create on server!!!"
    )
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentNews(){
        List<News> newsList = newsService.getCurrentNewsList();
        if(newsList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }

    @Operation(summary = "get all news",
            description = "only administrator have access to see all news"
    )
    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<News>> geAllNews(){
        List<News> newsList = newsService.getNewsList();
        if(newsList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }

    @Operation( summary = "get news by id",
    description = "get details about news"
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNews(@PathVariable("id") Long id){
        logger.info("Get news by id " + id);
        return new ResponseEntity<>(newsService.getNewsById(id), HttpStatus.OK);
    }

    @Operation(summary = "gives a list of promotion for 3 months ahead",
            description = "gives a list of promotion for 3 months ahead, date and time create on server!!!"
    )
    @GetMapping(value = "/promo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPromotion(){
        List<News> promotionList = newsService.getPromotionList();
        if(promotionList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(promotionList, HttpStatus.OK);
    }

    @Operation(summary = "Create News",
    description = "Only administrator can create news"
    )
    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNews(@Valid @RequestBody
                                       @Parameter(description = "entity News") News news){
        logger.info("Save entity.News " + news.toString());
        return new ResponseEntity<>(newsService.createNews(news).getId(), HttpStatus.CREATED);
    }

    @Operation(
            summary = "update news",
            description = "Save and flush news to db."
    )
    @PutMapping(value = "/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateNews(@PathVariable("id") Long id, @Valid @RequestBody News news) {

        logger.info("Update news with id " + id);
        News newsDB = newsService.getNewsById(id);

        if (newsDB == null) {
            logger.error("Unable to update. News with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (news.equals(newsDB)) {
            logger.error("A News with " + news.getId() + " already exist and the same!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        news.setId(id);
        newsService.updateNews(news);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary =" delete news by id",
            description = "delete news by ID"
    )
    @DeleteMapping(value = "admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNews(@PathVariable("id") Long id){
        logger.info("Fetching & Deleting news with id " + id);

        if(newsService.isExists(id)){
            newsService.deleteNews(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.error("Unable to delete. News with id " + id + " not found");
            return new ResponseEntity<>( "News with " + id + " isn't exists", HttpStatus.NOT_FOUND);
    }
}
