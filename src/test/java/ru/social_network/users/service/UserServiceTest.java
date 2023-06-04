package ru.social_network.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.social_network.users.entity.User;
import ru.social_network.users.exception.user.UserBadRequestException;
import ru.social_network.users.exception.user.UserNotFoundException;
import ru.social_network.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void findAll() {
        List<User> expectedUsers = buildExpectedUsers();
        when(userRepository.findAllByDeleted(false))
                .thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAll();

        assertThat(actualUsers)
                .isEqualTo(expectedUsers);
    }

    @Test
    void findAllReturnEmptyList() {
        when(userRepository.findAllByDeleted(false))
                .thenReturn(List.of());

        UserNotFoundException userNotFoundException = assertThrows(
                UserNotFoundException.class,
                () -> userService.findAll()
        );

        assertThat(userNotFoundException.getMessage())
                .isEqualTo("Пользователи не найдены");
    }

    @Test
    void find() {
        UUID userId = UUID.randomUUID();
        User expectedUser = buildExpectedUser(userId);
        when(userRepository.findByIdAndDeleted(userId, false))
                .thenReturn(Optional.of(expectedUser));

        User actualUser = userService.find(userId);

        assertThat(actualUser)
                .isEqualTo(expectedUser);
    }

    @Test
    void findReturnEmpty() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findByIdAndDeleted(userId, false))
                .thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = assertThrows(
                UserNotFoundException.class,
                () -> userService.find(userId)
        );

        assertThat(userNotFoundException.getMessage())
                .isEqualTo("Пользователь с id: " + userId + " - не найден");
    }

    @Test
    void save() {
        UUID userId = UUID.randomUUID();
        User expectedUser = buildExpectedUser(userId);
        when(userRepository.save(expectedUser))
                .thenReturn(expectedUser);

        assertDoesNotThrow(() -> userService.save(expectedUser));
    }

    @Test
    void delete() {
        UUID userId = UUID.randomUUID();
        User expectedUser = buildExpectedUser(userId);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(expectedUser));

        assertDoesNotThrow(() -> userService.delete(userId));

        verify(userRepository).softDelete(eq(userId), any());
    }

    @Test
    void deleteAgain() {
        UUID userId = UUID.randomUUID();
        User expectedUser = buildExpectedUser(userId, true);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(expectedUser));

        assertDoesNotThrow(() -> userService.delete(userId));

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteNotFoundUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());


        UserNotFoundException userNotFoundException = assertThrows(
                UserNotFoundException.class,
                () -> userService.delete(userId)
        );

        assertThat(userNotFoundException.getMessage())
                .isEqualTo("Пользователь с id: " + userId + " - не найден");
    }

    @Test
    void update() {
        UUID userId = UUID.randomUUID();
        User expectedUser = buildExpectedUser(userId);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(expectedUser));
        when(userRepository.save(expectedUser))
                .thenReturn(expectedUser);

        assertDoesNotThrow(() -> userService.update(userId, expectedUser));
    }

    @Test
    void updateNotFoundUser() {
        UUID userId = UUID.randomUUID();
        User expectedUser = buildExpectedUser(userId);
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());


        UserNotFoundException userNotFoundException = assertThrows(
                UserNotFoundException.class,
                () -> userService.update(userId, expectedUser)
        );
        assertThat(userNotFoundException.getMessage())
                .isEqualTo("Пользователь с id: " + userId + " - не найден");

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateWithDifferentId() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        User expectedUser = buildExpectedUser(userId1);

        UserBadRequestException userBadRequestException = assertThrows(
                UserBadRequestException.class,
                () -> userService.update(userId2, expectedUser)
        );
        assertThat(userBadRequestException.getMessage())
                .isEqualTo("id пользователя указан некорректно. id url: " + userId2 + ", id в теле запроса: " + userId1);

        verifyNoInteractions(userRepository);
    }

    private List<User> buildExpectedUsers() {
        User user = buildExpectedUser(UUID.randomUUID());
        return List.of(user);
    }

    private User buildExpectedUser(UUID userId) {
        return buildExpectedUser(userId, false);
    }

    private User buildExpectedUser(UUID userId, boolean isDeleted) {
        User user = new User();
        user.setId(userId);
        user.setFirstName("Василий");
        user.setLastName("Гуляев");
        user.setPhone("+79271408413");
        user.setInfo("Я профессиональный каскадер");
        user.setEmail("users@mail.ru");
        user.setDeleted(isDeleted);
        user.setLastModifyTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        return user;
    }
}