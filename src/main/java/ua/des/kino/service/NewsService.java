package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.mysql.News;

import java.util.List;

@Service
public interface NewsService {

    boolean isExists(Long id);

    List<News> getNewsList();

    List<News> getPromotionList();

    List<News> getCurrentNewsList();

    News getNewsById(Long id);

    void updateNews(News news);

    void deleteNews(Long id);

    News createNews(News news);
}
