package ru.social_network.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.social_network.users.entity.User;
import ru.social_network.users.repository.UserRepository;

import java.time.OffsetDateTime;
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
        return userRepository.findAll();
    }

    public User find(UUID id) {
        return userRepository.findById(id)
                .orElseGet(() -> {
                    log.warn("Пользователь с id: {} - не найден", id);
                    return null;
                });
    }

    public void save(User user) {
        user.setDeleted(false);
        user.setCreateTime(OffsetDateTime.now());
        user.setLastModifyTime(OffsetDateTime.now());
        User savedUser = userRepository.save(user);
        log.info("Пользователь: {} - успешно сохранен", savedUser.getId());
    }

    public void delete(UUID id) {
        userRepository.delete(id, OffsetDateTime.now());
        log.info("Пользователь: {} - успешно удален", id);
    }

    public void update(User user) {
        Optional.ofNullable(user.getId())
                .flatMap(userRepository::findById)
                .ifPresentOrElse(
                        finedUser -> {
                            user.setLastModifyTime(OffsetDateTime.now());
                            user.setCreateTime(finedUser.getCreateTime());
                            User savedUser = userRepository.save(user);
                            log.info("Пользователь: {} - успешно обновлен", savedUser.getId());
                        },
                        () -> {
                            throw new IllegalArgumentException("Пользователь с id: " + user.getId() + " - не найден");
                        }
                );
    }
}
