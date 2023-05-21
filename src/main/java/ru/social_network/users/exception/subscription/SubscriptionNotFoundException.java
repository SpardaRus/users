package ru.social_network.users.exception.subscription;

import java.util.UUID;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(String message) {
        super(message);
    }

    public SubscriptionNotFoundException(UUID subscriptionsId) {
        super("Подписка с id: " + subscriptionsId + " - не найдена");
    }
}
