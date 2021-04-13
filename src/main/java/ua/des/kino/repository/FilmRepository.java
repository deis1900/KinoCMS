package ua.des.kino.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.Film;

import javax.persistence.QueryHint;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findByNameLike(String filter);

    List<Film> getFilmsByStartDateAfterAndFinishDateBefore(Timestamp start, Timestamp finish);

    @QueryHints(value = { @QueryHint(name = "name", value = "value")}, forCounting = false)
    Page<Film> findByName(String name, Pageable pageable);
}