package ru.social_network.users.test.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.social_network.users.entity.Subscription;
import ru.social_network.users.test.BaseIntegration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest extends BaseIntegration {

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка успешного получения списка подписок на пользователя")
    void findAll() throws Exception {
        get("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions")
                .andExpect(content().json(
                        """
                                [
                                  {
                                    "id": "7f4d218e-f062-4169-8b68-b28626ab9244",
                                    "userId": "291253a6-f261-11ed-a05b-0242ac120003",
                                    "userSubscriberId": "202572d2-f261-11ed-a05b-0242ac120003",
                                    "deleted": false,
                                    "createTime": "2023-05-20T00:00:00",
                                    "lastModifyTime": "2023-05-20T00:00:00"
                                  }
                                ]
                                 """
                ))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Проверка получения ошибки при запросе списка подписок на пользователя при пустой бд")
    void findAllEmpty() throws Exception {
        get("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions")
                .andExpect(content().json(
                        """
                                {
                                    "message":"Подписки не найдены"
                                }
                                 """
                ))
                .andExpect(status().isNotFound());
    }

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

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка получения ошибки при создании подписки на пользователя указав в теле запроса и url разный userId")
    void subscribeNotCorrectId() throws Exception {
        post("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions",
                """
                        {
                            "userId": "291253a6-f261-11ed-a05b-0242ac120004",
                            "userSubscriberId": "a74a7726-f261-11ed-a05b-0242ac120003",
                            "deleted": false,
                            "createTime": "2023-05-20T00:00:00",
                            "lastModifyTime": "2023-05-20T00:00:00"
                        }
                        """)
                .andExpect(content().json(
                        """
                                {
                                    "message":"id пользователя указан некорректно. id url: 291253a6-f261-11ed-a05b-0242ac120003, id в теле запроса: 291253a6-f261-11ed-a05b-0242ac120004"
                                }
                                 """
                ))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка успешного обновления подписки на пользователя")
    void update() throws Exception {
        put("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244",
                """
                        {
                          "id": "7f4d218e-f062-4169-8b68-b28626ab9244",
                          "userId": "291253a6-f261-11ed-a05b-0242ac120003",
                          "userSubscriberId": "a74a7726-f261-11ed-a05b-0242ac120003",
                          "deleted": true,
                          "createTime": "2023-05-21T00:00:00",
                          "lastModifyTime": "2023-05-22T22:04:32"
                        }
                        """)
                .andExpect(status().isOk());

        Subscription actualSubscription = subscriptionRepository.findById(UUID.fromString("7f4d218e-f062-4169-8b68-b28626ab9244")).orElseThrow();
        assertAll("actualSubscription",
                () -> assertEquals(UUID.fromString("291253a6-f261-11ed-a05b-0242ac120003"), actualSubscription.getUserId()),
                () -> assertEquals(UUID.fromString("a74a7726-f261-11ed-a05b-0242ac120003"), actualSubscription.getUserSubscriberId()),
                () -> assertEquals(true, actualSubscription.getDeleted())
        );
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка получения ошибки при обновлении подписки на пользователя с некорректным userId. Отличается в url и теле запроса")
    void updateNotCorrectUserId() throws Exception {
        put("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244",
                """
                        {
                          "id": "7f4d218e-f062-4169-8b68-b28626ab9244",
                          "userId": "291253a6-f261-11ed-a05b-0242ac120004",
                          "userSubscriberId": "a74a7726-f261-11ed-a05b-0242ac120003",
                          "deleted": true,
                          "createTime": "2023-05-21T00:00:00",
                          "lastModifyTime": "2023-05-22T22:04:32"
                        }
                        """)
                .andExpect(content().json(
                        """
                                {
                                    "message":"id пользователя указан некорректно. id url: 291253a6-f261-11ed-a05b-0242ac120003, id в теле запроса: 291253a6-f261-11ed-a05b-0242ac120004"
                                }
                                 """
                ))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка получения ошибки при обновлении подписки на пользователя с некорректным subscriptionsId. Отличается в url и теле запроса")
    void updateNotCorrectSubscriptionsId() throws Exception {
        put("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244",
                """
                        {
                          "id": "7f4d218e-f062-4169-8b68-b28626ab9245",
                          "userId": "291253a6-f261-11ed-a05b-0242ac120003",
                          "userSubscriberId": "a74a7726-f261-11ed-a05b-0242ac120003",
                          "deleted": true,
                          "createTime": "2023-05-21T00:00:00",
                          "lastModifyTime": "2023-05-22T22:04:32"
                        }
                        """)
                .andExpect(content().json(
                        """
                                {
                                    "message":"id подписки указан некорректно. id url: 7f4d218e-f062-4169-8b68-b28626ab9244, id в теле запроса: 7f4d218e-f062-4169-8b68-b28626ab9245"
                                }
                                 """
                ))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Проверка получения ошибки при обновлении подписки на пользователя с пустой бд")
    void updateEmpty() throws Exception {
        put("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244",
                """
                        {
                          "id": "7f4d218e-f062-4169-8b68-b28626ab9244",
                          "userId": "291253a6-f261-11ed-a05b-0242ac120003",
                          "userSubscriberId": "a74a7726-f261-11ed-a05b-0242ac120003",
                          "deleted": true,
                          "createTime": "2023-05-21T00:00:00",
                          "lastModifyTime": "2023-05-22T22:04:32"
                        }
                        """)
                .andExpect(content().json(
                        """
                                {
                                    "message":"Подписка с id: 7f4d218e-f062-4169-8b68-b28626ab9244 - не найдена"
                                }
                                 """
                ))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка успешного удаления подписки на пользователя")
    void delete() throws Exception {
        delete("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244")
                .andExpect(status().isOk());

        Subscription actualSubscription = subscriptionRepository.findById(UUID.fromString("7f4d218e-f062-4169-8b68-b28626ab9244")).orElseThrow();
        assertAll("actualSubscription",
                () -> assertEquals(true, actualSubscription.getDeleted())
        );
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/insert_users.sql",
            "classpath:sql/insert_subscriptions.sql"
    })
    @DisplayName("Проверка успешного повторного удаления подписки на пользователя")
    void deleteRepeat() throws Exception {
        delete("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244")
                .andExpect(status().isOk());

        delete("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244")
                .andExpect(status().isOk());

        Subscription actualSubscription = subscriptionRepository.findById(UUID.fromString("7f4d218e-f062-4169-8b68-b28626ab9244")).orElseThrow();
        assertAll("actualSubscription",
                () -> assertEquals(true, actualSubscription.getDeleted())
        );
    }

    @Test
    @DisplayName("Проверка получения ошибки при удалении подписки на пользователя при пустой бд")
    void deleteEmpty() throws Exception {
        delete("/v1/users/291253a6-f261-11ed-a05b-0242ac120003/subscriptions/7f4d218e-f062-4169-8b68-b28626ab9244")
                .andExpect(content().json(
                        """
                                {
                                    "message":"Подписка с id: 7f4d218e-f062-4169-8b68-b28626ab9244 - не найдена"
                                }
                                 """
                ))
                .andExpect(status().isNotFound());
    }
}
