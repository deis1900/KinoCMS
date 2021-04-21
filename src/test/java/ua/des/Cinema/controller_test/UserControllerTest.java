package ua.des.Cinema.controller_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.des.kino.controller.UserController;
import ua.des.kino.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

//    @MockBean
//    UserService userService;
//
//    ObjectMapper mapper = new ObjectMapper();
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
//        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN);
//        String user = "{\"name\": \"bob\", \"email\" : \"bob@domain.com\"}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                .content(user)
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(textPlainUtf8));
//    }
//
//    @Test
//    public void whenPostRequestToUsersAndInValidUser_thenCorrectResponse() throws Exception {
//        String user = "{\"name\": \"\", \"email\" : \"bob@domain.com\"}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                .content(user)
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Name is mandatory")))
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON_VALUE));
//    }
//}

//    @Test
//    public void returnedCreationUser() throws Exception {
//        CreateUserRequest request = new CreateUserRequest();
//        request.setLogin("test user");
//
//        User user = new User();
//        user.setLogin(request.getLogin());
//
//        when(userService.save(any(CreateUserRequest.class))).thenReturn(user);
//
//        mockMvc.perform(post("/user")
//                .content(mapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("${login}").value(request.getLogin()));
//    }
//
//    @Data
//    private class CreateUserRequest {
//        private String login;
//        private String password;
//    }
}
