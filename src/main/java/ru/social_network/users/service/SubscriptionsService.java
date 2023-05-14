package ru.social_network.users.service;

import org.springframework.stereotype.Service;
import ru.social_network.users.entity.User;
import ru.social_network.users.repository.SubscriptionRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionsService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<User> findAll(UUID id) {
        return subscriptionRepository.findSubscriptionsByUserId(id);
    }
}
