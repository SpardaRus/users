package ru.social_network.users.exception.user;

import ru.social_network.users.annotaion.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class UserBadRequestException extends RuntimeException {

    public UserBadRequestException(String message) {
        super(message);
    }
}
