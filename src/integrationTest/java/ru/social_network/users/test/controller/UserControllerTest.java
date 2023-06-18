package ru.social_network.users.test.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.social_network.users.entity.User;
import ru.social_network.users.test.BaseIntegration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BaseIntegration {

    @Test
    @DisplayName("Проверка успешного получения списка пользователей")
    @Sql(scripts = {
            "classpath:sql/insert_users.sql"
    })
    void findAll() throws Exception {
        get("/v1/users")
                .andExpect(content().json(
                        """
                                [
                                   {
                                     "id": "7407c806-a6d8-4849-9fb9-71dc71a2aa91",
                                     "firstName": "Илья",
                                     "lastName": "Журавлев",
                                     "email": "ilya@yandex.ru",
                                     "phone": "+79271408413",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   },
                                   {
                                     "id": "202572d2-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Никита",
                                     "lastName": "Подписывающий",
                                     "email": "nikita@yandex.ru",
                                     "phone": "+79271408414",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   },
                                   {
                                     "id": "a74a7726-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Рома",
                                     "lastName": "Сафронов",
                                     "email": "roma@yandex.ru",
                                     "phone": "+79271408415",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   },
                                   {
                                     "id": "291253a6-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Вася",
                                     "lastName": "Уважаемый",
                                     "email": "vasya@yandex.ru",
                                     "phone": "+79271408416",
                                     "info": null,
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
    @DisplayName("Проверка успешного получения списка пользователей с удалением пользователей")
    @Sql(scripts = {
            "classpath:sql/insert_users.sql"
    })
    void findAllWithDelete() throws Exception {
        get("/v1/users")
                .andExpect(content().json(
                        """
                                [
                                   {
                                     "id": "7407c806-a6d8-4849-9fb9-71dc71a2aa91",
                                     "firstName": "Илья",
                                     "lastName": "Журавлев",
                                     "email": "ilya@yandex.ru",
                                     "phone": "+79271408413",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   },
                                   {
                                     "id": "202572d2-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Никита",
                                     "lastName": "Подписывающий",
                                     "email": "nikita@yandex.ru",
                                     "phone": "+79271408414",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   },
                                   {
                                     "id": "a74a7726-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Рома",
                                     "lastName": "Сафронов",
                                     "email": "roma@yandex.ru",
                                     "phone": "+79271408415",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   },
                                   {
                                     "id": "291253a6-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Вася",
                                     "lastName": "Уважаемый",
                                     "email": "vasya@yandex.ru",
                                     "phone": "+79271408416",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   }
                                ]
                                """
                ))
                .andExpect(status().isOk());

        delete("/v1/users/7407c806-a6d8-4849-9fb9-71dc71a2aa91")
                .andExpect(status().isOk());

        delete("/v1/users/a74a7726-f261-11ed-a05b-0242ac120003")
                .andExpect(status().isOk());

        get("/v1/users")
                .andExpect(content().json(
                        """
                                [
                                   {
                                     "id": "202572d2-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Никита",
                                     "lastName": "Подписывающий",
                                     "email": "nikita@yandex.ru",
                                     "phone": "+79271408414",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                   },
                                   {
                                     "id": "291253a6-f261-11ed-a05b-0242ac120003",
                                     "firstName": "Вася",
                                     "lastName": "Уважаемый",
                                     "email": "vasya@yandex.ru",
                                     "phone": "+79271408416",
                                     "info": null,
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
    @DisplayName("Проверка получения ошибки при запросе получения списка пользователей в пустой базе")
    void findAllEmptyUsers() throws Exception {
        get("/v1/users")
                .andExpect(content().json(
                        """
                                {
                                    "message":"Пользователи не найдены"
                                }
                                """
                ))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Проверка успешного получения одного пользователя")
    @Sql(scripts = {
            "classpath:sql/insert_users.sql"
    })
    void find() throws Exception {
        get("/v1/users/7407c806-a6d8-4849-9fb9-71dc71a2aa91")
                .andExpect(content().json(
                        """
                                {
                                     "id": "7407c806-a6d8-4849-9fb9-71dc71a2aa91",
                                     "firstName": "Илья",
                                     "lastName": "Журавлев",
                                     "email": "ilya@yandex.ru",
                                     "phone": "+79271408413",
                                     "info": null,
                                     "deleted": false,
                                     "createTime": "2023-05-20T00:00:00",
                                     "lastModifyTime": "2023-05-20T00:00:00"
                                }
                                """
                ))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Проверка получения ошибки при запросе получения одного пользователя в пустой базе")
    void findEmptyUsers() throws Exception {
        get("/v1/users/7407c806-a6d8-4849-9fb9-71dc71a2aa91")
                .andExpect(content().json(
                        """
                                {
                                    "message":"Пользователь с id: 7407c806-a6d8-4849-9fb9-71dc71a2aa91 - не найден"
                                }
                                """
                ))
                .andExpect(status().isNotFound());
    }

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

    @Test
    @DisplayName("Проверка успешного обновления пользователя")
    @Sql(scripts = {
            "classpath:sql/insert_users.sql"
    })
    void update() throws Exception {
        String userId = "7407c806-a6d8-4849-9fb9-71dc71a2aa91";
        put("/v1/users/" + userId,
                """
                        {
                            "id": "7407c806-a6d8-4849-9fb9-71dc71a2aa91",
                            "firstName": "Рома4",
                            "lastName": "Сафронов24",
                            "email": "roma2@yandex.ru",
                            "phone": "+792714084122",
                            "deleted": false
                        }
                        """)
                .andExpect(status().isOk());

        User actualUser = userRepository.findById(UUID.fromString(userId)).orElseThrow();
        assertAll("actualUser",
                () -> assertEquals("Рома4", actualUser.getFirstName()),
                () -> assertEquals("Сафронов24", actualUser.getLastName()),
                () -> assertEquals("roma2@yandex.ru", actualUser.getEmail()),
                () -> assertEquals("+792714084122", actualUser.getPhone()),
                () -> assertEquals(false, actualUser.getDeleted())
        );
    }

    @Test
    @DisplayName("Проверка получения ошибки при запросе обновления пользователя с несовпадающим id url и в теле запроса")
    @Sql(scripts = {
            "classpath:sql/insert_users.sql"
    })
    void updateNotCorrectId() throws Exception {
        put("/v1/users/7407c806-a6d8-4849-9fb9-71dc71a2aa91",
                """
                        {
                            "id": "7407c806-a6d8-4849-9fb9-71dc71a2aa92",
                            "firstName": "Рома4",
                            "lastName": "Сафронов24",
                            "email": "roma2@yandex.ru",
                            "phone": "+792714084122",
                            "deleted": false
                        }
                        """)
                .andExpect(content().json(
                        """
                                {
                                    "message":"id пользователя указан некорректно. id url: 7407c806-a6d8-4849-9fb9-71dc71a2aa91, id в теле запроса: 7407c806-a6d8-4849-9fb9-71dc71a2aa92"
                                }
                                """
                ))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Проверка получения ошибки при запросе обновления пользователя в пустой бд")
    void updateEmpty() throws Exception {
        put("/v1/users/7407c806-a6d8-4849-9fb9-71dc71a2aa91",
                """
                        {
                            "id": "7407c806-a6d8-4849-9fb9-71dc71a2aa91",
                            "firstName": "Рома4",
                            "lastName": "Сафронов24",
                            "email": "roma2@yandex.ru",
                            "phone": "+792714084122",
                            "deleted": false
                        }
                        """)
                .andExpect(content().json(
                        """
                                {
                                    "message":"Пользователь с id: 7407c806-a6d8-4849-9fb9-71dc71a2aa91 - не найден"
                                }
                                """
                ))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Проверка успешного удаления пользователя")
    @Sql(scripts = {
            "classpath:sql/insert_users.sql"
    })
    void delete() throws Exception {
        String userId = "7407c806-a6d8-4849-9fb9-71dc71a2aa91";
        delete("/v1/users/" + userId)
                .andExpect(status().isOk());

        assertThat(userRepository.findAll())
                .isNotEmpty()
                .hasSize(4);

        User actualUser = userRepository.findById(UUID.fromString(userId)).orElseThrow();
        assertAll("actualUser",
                () -> assertEquals("Илья", actualUser.getFirstName()),
                () -> assertEquals("Журавлев", actualUser.getLastName()),
                () -> assertEquals("ilya@yandex.ru", actualUser.getEmail()),
                () -> assertEquals("+79271408413", actualUser.getPhone()),
                () -> assertEquals(true, actualUser.getDeleted())
        );
    }

    @Test
    @DisplayName("Проверка повторного удаления пользователя")
    @Sql(scripts = {
            "classpath:sql/insert_users.sql"
    })
    void deleteRepeat() throws Exception {
        String userId = "7407c806-a6d8-4849-9fb9-71dc71a2aa91";
        delete("/v1/users/" + userId)
                .andExpect(status().isOk());

        delete("/v1/users/" + userId)
                .andExpect(status().isOk());

        assertThat(userRepository.findAll())
                .isNotEmpty()
                .hasSize(4);
    }

    @Test
    @DisplayName("Проверка получения ошибки при запросе удаления пользователя в пустой бд")
    void deleteEmpty() throws Exception {
        delete("/v1/users/7407c806-a6d8-4849-9fb9-71dc71a2aa91")
                .andExpect(content().json(
                        """
                                {
                                    "message":"Пользователь с id: 7407c806-a6d8-4849-9fb9-71dc71a2aa91 - не найден"
                                }
                                """
                ))
                .andExpect(status().isNotFound());
    }
}
