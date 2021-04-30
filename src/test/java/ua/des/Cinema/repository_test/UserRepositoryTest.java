package ua.des.Cinema.repository_test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.CinemaApplication;
import ua.des.kino.model.User;
import ua.des.kino.model.submodel.Sex;
import ua.des.kino.model.submodel.UserContact;
import ua.des.kino.repository.UserRepository;
import ua.des.kino.model.submodel.UserDetails;
import ua.des.kino.model.submodel.UserLang;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)
@Transactional
public class UserRepositoryTest {
    private Long id = -1L;

    @Autowired
    private UserRepository repository;

    @Before
    public void init() {
        repository.save(newUser());
        Optional<User> queryResult = repository.findUserByLogin("DannyTest");
        assertFalse(queryResult.isEmpty());
        id = queryResult.get().getId();
    }

    @After
    public void clean() {
        repository.delete(newUser());
        repository.deleteById(id);
    }

    @Test
    public void findByLoginAndPasswordTest() {
        String password = "TestPass";
        String login = "DannyTest";
        List<User> queryResult = repository.findByLoginAndPassword(login, password);
        assertFalse(queryResult.isEmpty());
        assertNotNull(queryResult.get(0));
        assertEquals(login, queryResult.get(0).getLogin());
        Arrays.toString(queryResult.toArray());
    }

    @Test
    public void findContactDataTest() {
        Optional<User> actualUser = repository.findUserByLogin("DannyTest");
        assertTrue(actualUser.isPresent());
        assertNotNull(actualUser.get().getContact());

        UserContact actual = actualUser.get().getContact();
        System.out.println(actual.toString());
        repository.deleteById(actualUser.get().getId());
        assertEquals(newUser().getContact(), actual);
    }

    @Test
    public void findDetailsDataTest() {
        Optional<User> actualUser = repository.findUserByLogin("DannyTest");
        assertTrue(actualUser.isPresent());
        assertNotNull(actualUser.get().getDetails());

        UserDetails expected = newUser().getDetails();
        UserDetails actual = actualUser.get().getDetails();

        System.out.println("\n" + actual.toString() + "\n");
        repository.deleteById(actualUser.get().getId());
        assertEquals(expected, actual);
    }

    private static User newUser() {
        UserContact contact = new UserContact();
        contact.setAddress("TestAddress");
        contact.setCard("0000-0000-0000-0000");
        contact.setEmail("danny@Test.com");
        contact.setPhone("+38(066)-000-00-00");
        contact.setName("Danny");
        contact.setSurname("Test");

        UserDetails details = new UserDetails();
        details.setBirthday(LocalDate.of(1999, 12, 21));
        details.setCity("Mexico");
        details.setLanguage(UserLang.RU);
        details.setSex(Sex.MAN);

        User user = new User();
        user.setLogin("DannyTest");
        user.setPassword("TestPass");
        user.setContact(contact);
        user.setDetails(details);
        return user;
    }
}

