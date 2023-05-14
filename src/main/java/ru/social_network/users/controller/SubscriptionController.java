package ru.social_network.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.social_network.users.entity.User;
import ru.social_network.users.service.SubscriptionsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionsService subscriptionsService;

    public SubscriptionController(SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/{id}")
    public List<User> findAll(@PathVariable UUID id) {
        return subscriptionsService.findAll(id);
    }
}
