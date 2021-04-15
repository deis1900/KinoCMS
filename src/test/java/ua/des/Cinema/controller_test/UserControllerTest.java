package ua.des.Cinema.controller_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.des.kino.controller.AdminController;
import ua.des.kino.controller.UserController;
import ua.des.kino.model.User;
import ua.des.kino.model.submodel.Photo;
import ua.des.kino.service.UserService;
import ua.des.kino.service.util.PhotoService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void returnedCreationUser() throws Exception {
//        CreateUserRequest request = new CreateUserRequest();
//        request.setName("test user");
//
//        User user = new User();
//        user.setLogin(request.getName());
//
//        when(userService.save(any(CreateUserRequest.class))).thenReturn(user);
//
//        mockMvc.perform(post("/user")
//                .content(mapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$name").value(request.getName()));
//    }
//
//    @Datapublic
//    class CreateUserRequest {
//        private String name;
//        private String email;
//        private int age;
//    }
}
