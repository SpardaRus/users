package ru.social_network.users.exception.subscription;

public class SubscriptionBadRequestException extends RuntimeException {

    public SubscriptionBadRequestException(String message) {
        super(message);
    }
}
