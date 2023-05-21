package ru.social_network.users.controller;

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

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public void save(@RequestBody User user) {
        userService.save(user);
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable UUID userId, @RequestBody User user) {
        userService.update(userId, user);
    }

    @GetMapping("/{userId}")
    public User find(@PathVariable UUID userId) {
        return userService.find(userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable UUID userId) {
        userService.delete(userId);
    }

}
