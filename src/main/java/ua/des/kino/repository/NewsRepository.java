package ua.des.kino.repository;

import org.springframework.data.repository.Repository;
import ua.des.kino.model.News;

@org.springframework.stereotype.Repository
public interface NewsRepository extends Repository<News, Long> {


}
