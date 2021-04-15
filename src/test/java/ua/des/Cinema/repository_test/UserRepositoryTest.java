package ua.des.Cinema.repository_test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.des.kino.CinemaApplication;
import ua.des.kino.model.User;
import ua.des.kino.model.submodel.Sex;
import ua.des.kino.model.submodel.UserContact;
import ua.des.kino.model.submodel.UserDetails;
import ua.des.kino.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Before
    public void saveTest() {
        repository.save(newUser());

        Optional<User> queryResult = repository.getUserByLogin("DannyTest");
        assertFalse(queryResult.isEmpty());
    }

    @Test
    public void findByLoginAndPassword() {
        String password = "TestPass";
        String login = "DannyTest";
        List<User> queryResult =repository.findByLoginAndPassword(login, password);
        assertFalse(queryResult.isEmpty());
        assertNotNull(queryResult.get(0));
        assertEquals(login, queryResult.get(0).getLogin());
    }

    @After
    public void deleteUser() {
        repository.delete(newUser());
    }

    private User newUser() {
        UserContact contact = new UserContact();
        contact.setId(12L);
        contact.setAddress("TestAddress");
        contact.setCard("0000-0000-0000-0000");
        contact.setEmail("danny@Test.com");
        contact.setPhone("+38(066)-000-00-00");
        contact.setName("Danny");
        contact.setSurname("Test");

        UserDetails details = new UserDetails();
        details.setId(12L);
        details.setBirthday(new Date());
        details.setCity("Mexico");
        details.setLanguage("EN");
        details.setSex(Sex.MAN);

        User user = new User();
        contact.setUser(user);
        details.setUser(user);
        user.setId(12L);
        user.setLogin("DannyTest");
        user.setPassword("TestPass");
        user.setContact(new UserContact());
        user.setDetails(new UserDetails());
        return user;
    }
}

