package ru.social_network.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void save() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "firstName": "Рома2",
                                    "lastName": "Сафронов2",
                                    "email": "roma12@yandex.ru",
                                    "phone": "+792714084123",
                                    "deleted": false
                                }
                                """))
                .andReturn();
        int httpStatus = result.getResponse().getStatus();
        assertThat(httpStatus)
                .isEqualTo(HttpStatus.OK.value());
    }
}
