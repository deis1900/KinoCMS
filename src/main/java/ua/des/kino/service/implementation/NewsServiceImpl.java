package ua.des.kino.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.mysql.News;
import ua.des.kino.model.mysql.submodel.NewsType;
import ua.des.kino.repository.mysql.NewsRepository;
import ua.des.kino.service.NewsService;
import ua.des.kino.util.exception_handler.EntityIdMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public boolean isExists(Long id) {
        return newsRepository.existsById(id);
    }

    @Override
    public List<News> getNewsList() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> getPromotionList() {
        List<News> newsList = getNewsListDB();
        return newsList.stream()
                .filter(News::getState)
                .filter(news -> news.getNewsType().equals(NewsType.PROMOTION))
                .collect(Collectors.toList());
    }

    @Override
    public List<News> getCurrentNewsList() {
        return getNewsListDB().stream()
                .filter(News::getState)
                .filter(news -> news.getNewsType().equals(NewsType.NEWS))
                .collect(Collectors.toList());
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityIdMismatchException("News with id " + id + " isn't exist. ", new Throwable()));
    }

    @Transactional
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    @Transactional
    public void updateNews(News news) {
        newsRepository.saveAndFlush(news);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    private List<News> getNewsListDB() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plusMonths(3L);

        List<News> newsList = newsRepository.findAllByStateTrue();
        return newsList.stream()
                .filter(news -> now.isAfter(news.getStartDate()))
                .filter(news -> news.getStartDate().isBefore(limit))
                .collect(Collectors.toList());
    }
}
