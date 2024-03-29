package ua.des.Cinema.repository_test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.CinemaApplication;
import ua.des.kino.model.mysql.Film;
import ua.des.kino.model.mysql.Session;
import ua.des.kino.model.mysql.submodel.FilmDetails;
import ua.des.kino.repository.mysql.FilmRepository;
import ua.des.kino.repository.mysql.ShowtimeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.testng.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)
@Transactional
public class FilmRepositoryTest {

    @Autowired
    private FilmRepository repository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Before
    public void saveTest() {
        repository.save(newFilm());
        Film queryResult = repository.getOne(1L);
        assertNotNull(queryResult);
    }

    @Test
    public void getfilmToday(){
        repository.getFilmsByStartDateAfterAndFinishDateBefore(
                LocalDate.of(2021, 3,12),
                LocalDate.of(2021, 5,12));
    }

    @Test
    public void getCurrentSessionToday(){
        List<Session> sessionList = showtimeRepository.findSessionsByShowTimeBetweenOrderByShowTimeAsc(
                LocalDateTime.of(2021, 4,12,7, 5, 0),
                LocalDateTime.of(2021, 5,12,12, 50, 0));
        sessionList.forEach(System.out::println);
        assertNotNull(sessionList);

    }

    private Film newFilm() {
        Film film = new Film();
        film.setName("TestFilm");
        film.setStartDate(LocalDate.of(2021,1,1));
        film.setFinishDate(LocalDate.of(2022, 1, 1));

        FilmDetails filmDetails = new FilmDetails();
        filmDetails.setActors("TestActors");
        filmDetails.setCompositor("testCompositor");
        filmDetails.setBudget(200);
        film.setFilmDetails(filmDetails);
        return film;
    }

}
