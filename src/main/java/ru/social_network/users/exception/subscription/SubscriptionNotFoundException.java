package ru.social_network.users.exception.subscription;

import ru.social_network.users.annotaion.ExcludeFromJacocoGeneratedReport;

import java.util.UUID;

@ExcludeFromJacocoGeneratedReport
public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(String message) {
        super(message);
    }

    public SubscriptionNotFoundException(UUID subscriptionId) {
        super("Подписка с id: " + subscriptionId + " - не найдена");
    }
}
