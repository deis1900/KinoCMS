package ua.des.Cinema.repository_test;

import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.CinemaApplication;
import ua.des.kino.repository.UserRepository;
import ua.des.kino.model.User;
import ua.des.kino.model.submodel.Sex;
import ua.des.kino.model.submodel.UserContact;
import ua.des.kino.model.submodel.UserDetails;
import ua.des.kino.model.submodel.UserLang;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)
public class UserRepositoryCustomTest {

    Long id = -1L;

    @Autowired
    private UserRepository userRepository;

//    @Before
//    public void init() {
//        testUserRepositoryC.save(newUser());
//        Optional<User> user = userRepository.findUserByLogin("AfonyaTest");
//        user.ifPresent(value -> id = value.getId());
//    }
//
//    @After
//    public void clean() {
//        testUserRepositoryC.remove(id);
//        Optional<User> user = userRepository.findUserByLogin("AfonyaTest");
//        user.ifPresent(value -> System.out.println(value.getLogin() + " \n" + value.getContact().toString()));}
//
//    @Test(expected = LazyInitializationException.class)
//    public void whenAccessLazyCollection_thenThrowLazyInitializationException() {
//        testUserRepositoryC.save(newUser());
//        Optional<User> user1 = userRepository.findUserByLogin("AfonyaTest");
//        user1.ifPresent(value -> id = value.getId());
//        System.out.println(id);
//
//        System.out.println(user1.get().toString());
//
//        User user = testUserRepositoryC.findById(id);
//        assertNotNull(user.getContact().getEmail());
//        testUserRepositoryC.remove(id);
//    }
//
//    @Test
//    public void whenUseJPAQL_thenFetchResult() {
//        User user = testUserRepositoryC.findUserByJPQL(id);
//        assertEquals(newUser().getContact(), user.getContact());
//        testUserRepositoryC.remove(id);
//    }
//
//    @Test
//    public void whenUseEntityGraph_thenFetchResult() {
//        User user = testUserRepositoryC.findByEntityGraph(id);
//        assertEquals(newUser().getContact(), user.getContact());
//        testUserRepositoryC.remove(id);
//    }
//
//    @Test
//    @Transactional
//    public void whenUseTransaction_thenFetchResult() {
//        User user = testUserRepositoryC.findById(id);
//        System.out.println(user);
//        System.out.println(user.getContact());
//        assertEquals(newUser().getContact(), user.getContact());
//        testUserRepositoryC.remove(id);
//    }

    private static User newUser() {
        UserContact contact = new UserContact();
        contact.setAddress("TestAddress");
        contact.setCard("0000-0000-0000-0000");
        contact.setEmail("afonia@gmail.com");
        contact.setPhone("+38(066)-000-00-00");
        contact.setName("Afonya");
        contact.setSurname("Test");

        UserDetails details = new UserDetails();
        details.setBirthday(LocalDate.of(2004, 12, 21));
        details.setCity("Rome");
        details.setLanguage(UserLang.UA);
        details.setSex(Sex.MAN);

        User user = new User();
        user.setLogin("AfonyaTest");
        user.setPassword("TestPass");
        user.setContact(contact);
        user.setDetails(details);
        return user;
    }
}
