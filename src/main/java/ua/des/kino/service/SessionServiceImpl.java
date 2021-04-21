package ua.des.kino.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.Session;
import ua.des.kino.daos.repository.SessionEntityRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    public static final Logger logger = LoggerFactory.getLogger(FilmServiceImpl.class.getName());

    private final SessionEntityRepository sessionEntityRepository;

    public SessionServiceImpl(SessionEntityRepository sessionEntityRepository) {
        this.sessionEntityRepository = sessionEntityRepository;
    }

    @Override
    @Transactional
    public List<Session> getCurrentFilms(LocalDateTime start) {
//        TODO: sort response by date
            return sessionEntityRepository.findSessionsByShowTimeAfter(start);
    }

    @Override
    @Transactional
    public List<Session> findSessionsOnMonth(LocalDateTime start) {
        //        TODO: sort response by date
        var end = start.plusDays(30);
            return sessionEntityRepository.findSessionsByShowTimeBetween(start, end);
    }
}
