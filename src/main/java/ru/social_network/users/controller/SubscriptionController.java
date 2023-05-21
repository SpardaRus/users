package ru.social_network.users.controller;

import org.springframework.web.bind.annotation.*;
import ru.social_network.users.entity.Subscription;
import ru.social_network.users.service.SubscriptionsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class SubscriptionController {

    private final SubscriptionsService subscriptionsService;

    public SubscriptionController(SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/{userId}/subscriptions")
    public List<Subscription> findAll(@PathVariable UUID userId) {
        return subscriptionsService.findAll(userId);
    }

    @PostMapping("/{userId}/subscriptions")
    public void subscribe(@PathVariable UUID userId, @RequestBody Subscription subscription) {
        subscriptionsService.subscribe(userId, subscription);
    }

    @PutMapping("/{userId}/subscriptions/{subscriptionsId}")
    public void update(@PathVariable UUID userId, @PathVariable UUID subscriptionsId, @RequestBody Subscription subscription) {
        subscriptionsService.update(userId, subscriptionsId, subscription);
    }

    @DeleteMapping("/{userId}/subscriptions/{subscriptionsId}")
    public void unsubscribe(@PathVariable UUID userId, @PathVariable UUID subscriptionsId) {
        subscriptionsService.unsubscribe(userId, subscriptionsId);
    }
}
