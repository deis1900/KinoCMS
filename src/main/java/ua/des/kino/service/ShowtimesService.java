package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.mysql.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public interface ShowtimesService {

    Session findById(Long id);

    List<Session> getCurrentFilms(LocalDateTime start);

    List<Session> findSessionsOnMonth(LocalDateTime start);

    List<Session> finAllSessions();

    List<Session> saveAll(Set<Session> sessions);

    Session update(Session session);

    void delete(Long id);
}
