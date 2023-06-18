package ru.social_network.users.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.social_network.users.BaseIntegration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest extends BaseIntegration {

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка успешной подписки на пользователя")
    void subscribe() throws Exception {
        post("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions",
                """
                        {
                            "userId": "291253a6-f261-11ed-a05b-0242ac120003",
                            "userSubscriberId": "a74a7726-f261-11ed-a05b-0242ac120003",
                            "deleted": false,
                            "createTime": "2023-05-20T00:00:00",
                            "lastModifyTime": "2023-05-20T00:00:00"
                        }
                        """)
                .andExpect(status().isOk());

        assertThat(subscriptionRepository.findAll())
                .isNotEmpty()
                .hasSize(3);
    }
}
