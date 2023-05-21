package ru.social_network.users.exception.user;

public class UserBadRequestException extends RuntimeException {

    public UserBadRequestException(String message) {
        super(message);
    }
}
