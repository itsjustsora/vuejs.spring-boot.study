package com.taskagile.web.apis;

import com.taskagile.config.SecurityConfiguration;
import com.taskagile.domain.appilcation.UserService;
import com.taskagile.domain.model.user.UsernameExistsException;
import com.taskagile.utils.JsonUtils;
import com.taskagile.web.payload.RegistrationPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityConfiguration.class, RegistrationApiController.class})
@WebMvcTest()
public class RegistrationApiControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService serviceMock;

    @Test
    public void register_blankPayload_shouldFailAndReturn400() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/registrations"))
                .andExpect(status().is(400));
    }

    @Test
    public void register_existedUsername_shouldFailAndReturn400() throws Exception {
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername("exist");
        payload.setEmailAddress("test@taskagile.com");
        payload.setPassword("MyPassword!");

        doThrow(UsernameExistsException.class)
                .when(serviceMock)
                .register(payload.toCommand());

        mvc.perform(MockMvcRequestBuilders
                .post("/api/registrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(payload)))
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Username already exists"));
    }

    @Test
    public void register_validPayload_shouldSucceedAndReturn201() throws Exception {
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername("sunny");
        payload.setEmailAddress("sunny@taskagile.com");
        payload.setPassword("MyPassword!");

        doNothing().when(serviceMock)
                .register(payload.toCommand());

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(payload)))
                .andExpect(status().is(201));
    }
}
