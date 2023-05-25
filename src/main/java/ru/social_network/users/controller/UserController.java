package ru.social_network.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.social_network.users.entity.User;
import ru.social_network.users.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Добавление пользователя")
    @PostMapping
    public void save(@RequestBody User user) {
        userService.save(user);
    }

    @Operation(summary = "Обновление пользователя")
    @PutMapping("/{userId}")
    public void update(@PathVariable UUID userId, @RequestBody User user) {
        userService.update(userId, user);
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping("/{userId}")
    public User find(@PathVariable UUID userId) {
        return userService.find(userId);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable UUID userId) {
        userService.delete(userId);
    }

}
