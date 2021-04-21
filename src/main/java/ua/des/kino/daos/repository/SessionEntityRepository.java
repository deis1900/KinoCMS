package ua.des.kino.daos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.Session;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionEntityRepository extends JpaRepository<Session, Long> {

    List<Session> findSessionsByShowTimeAfter(LocalDateTime start);

    List<Session> findSessionsByShowTimeBetween(LocalDateTime start, LocalDateTime finish);
}
