package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.Session;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface SessionService {

    List<Session> getCurrentFilms(LocalDateTime start);

    List<Session> findSessionsOnMonth(LocalDateTime start);
}
