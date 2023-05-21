package ru.social_network.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.social_network.users.exception.controller.ErrorMessage;
import ru.social_network.users.exception.subscription.SubscriptionBadRequestException;
import ru.social_network.users.exception.subscription.SubscriptionNotFoundException;
import ru.social_network.users.exception.user.UserBadRequestException;
import ru.social_network.users.exception.user.UserNotFoundException;

@Slf4j
@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(SubscriptionBadRequestException.class)
    public ResponseEntity<ErrorMessage> subscriptionBadRequestException(SubscriptionBadRequestException e) {
        return defaultHandler(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorMessage> subscriptionNotFoundException(SubscriptionNotFoundException e) {
        return defaultHandler(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<ErrorMessage> userBadRequestException(UserBadRequestException e) {
        return defaultHandler(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException e) {
        return defaultHandler(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorMessage> defaultHandler(String e, HttpStatus badRequest) {
        log.warn(e);
        return ResponseEntity
                .status(badRequest)
                .body(new ErrorMessage(e));
    }
}
