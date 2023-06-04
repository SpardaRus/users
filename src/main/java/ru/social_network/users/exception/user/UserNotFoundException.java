package ru.social_network.users.exception.user;

import ru.social_network.users.annotaion.ExcludeFromJacocoGeneratedReport;

import java.util.UUID;

@ExcludeFromJacocoGeneratedReport
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(UUID userId) {
        super("Пользователь с id: " + userId + " - не найден");
    }
}
