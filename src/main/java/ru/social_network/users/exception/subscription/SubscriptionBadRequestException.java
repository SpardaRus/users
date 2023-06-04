package ru.social_network.users.exception.subscription;

import ru.social_network.users.annotaion.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class SubscriptionBadRequestException extends RuntimeException {

    public SubscriptionBadRequestException(String message) {
        super(message);
    }
}
