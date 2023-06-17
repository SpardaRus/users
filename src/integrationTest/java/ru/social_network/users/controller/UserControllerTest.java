package ru.social_network.users.controller;

import org.junit.jupiter.api.Test;
import ru.social_network.users.BaseIntegration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BaseIntegration {

    @Test
    void save() throws Exception {
        post("/v1/users",
                """
                        {
                            "firstName": "Рома2",
                            "lastName": "Сафронов2",
                            "email": "roma12@yandex.ru",
                            "phone": "+792714084123",
                            "deleted": false
                        }
                        """)
                .andExpect(status().isOk());
    }
}
