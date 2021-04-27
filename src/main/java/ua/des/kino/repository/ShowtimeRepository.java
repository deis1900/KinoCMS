package ua.des.kino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.Session;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Session, Long> {

    @Query("select s from Session s where s.id in :ids")
    List<Session> findBySessionIds(@Param("ids") Iterable<Long> ids);

    List<Session> findSessionsByShowTimeAfterOrderByShowTime(LocalDateTime start);

    List<Session> findSessionsByShowTimeBetweenOrderByShowTimeAsc(LocalDateTime start, LocalDateTime finish);
}
