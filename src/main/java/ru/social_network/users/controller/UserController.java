package ru.social_network.users.controller;

import org.springframework.web.bind.annotation.*;
import ru.social_network.users.entity.User;
import ru.social_network.users.service.UserService;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public User find(@PathVariable UUID id) {
        return userService.find(id);
    }

    @PostMapping
    public void save(@RequestBody User user) {
        userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody User user) {
        userService.update(user);
    }


}
