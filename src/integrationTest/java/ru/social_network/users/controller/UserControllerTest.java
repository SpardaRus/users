package ru.social_network.users.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.social_network.users.BaseIntegration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BaseIntegration {

    @Test
    @DisplayName("Проверка успешного создания пользователя")
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

        assertThat(userRepository.findAll())
                .isNotEmpty()
                .hasSize(1);
    }
}
