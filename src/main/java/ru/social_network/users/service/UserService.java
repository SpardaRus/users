package ru.social_network.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.social_network.users.entity.User;
import ru.social_network.users.exception.user.UserBadRequestException;
import ru.social_network.users.exception.user.UserNotFoundException;
import ru.social_network.users.repository.UserRepository;
import ru.social_network.users.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAllByDeleted(false);
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователи не найдены");
        }
        return users;
    }

    public User find(UUID userId) {
        return userRepository.findByIdAndDeleted(userId, false)
                .orElseThrow(() -> {
                    throw new UserNotFoundException(userId);
                });
    }

    public void save(User user) {
        LocalDateTime now = DateUtils.now();
        user.setCreateTime(now);
        user.setLastModifyTime(now);

        User savedUser = userRepository.save(user);
        log.info("Пользователь: {} - успешно сохранен", savedUser.getId());
    }

    @Transactional
    public void delete(UUID userId) throws IllegalArgumentException {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        foundUser -> {
                            if (Boolean.TRUE.equals(foundUser.getDeleted())) {
                                log.warn("Пользователь: {} - уже удален", foundUser.getId());
                            } else {
                                userRepository.softDelete(userId, DateUtils.now());
                                log.info("Пользователь: {} - успешно удален", userId);
                            }
                        },
                        () -> {
                            throw new UserNotFoundException(userId);
                        }
                );
    }

    @Transactional
    public void update(UUID userId, User user) {
        verifyIdOrThrow(userId, user);

        Optional.ofNullable(user.getId())
                .flatMap(userRepository::findById)
                .ifPresentOrElse(
                        foundUser -> {
                            user.setLastModifyTime(DateUtils.now());
                            user.setCreateTime(foundUser.getCreateTime());
                            User savedUser = userRepository.save(user);
                            log.info("Пользователь: {} - успешно обновлен", savedUser.getId());
                        },
                        () -> {
                            throw new UserNotFoundException(user.getId());
                        }
                );
    }

    private void verifyIdOrThrow(UUID userId, User user) {
        if (!userId.equals(user.getId())) {
            throw new UserBadRequestException("id пользователя указан некорректно. id url: " + userId + ", id в теле запроса: " + user.getId());
        }
    }
}
