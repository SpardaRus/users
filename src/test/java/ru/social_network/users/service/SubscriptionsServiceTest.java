package ru.social_network.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.social_network.users.entity.Subscription;
import ru.social_network.users.exception.subscription.SubscriptionBadRequestException;
import ru.social_network.users.exception.subscription.SubscriptionNotFoundException;
import ru.social_network.users.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionsServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    private SubscriptionsService subscriptionsService;

    @BeforeEach
    void setUp() {
        subscriptionsService = new SubscriptionsService(subscriptionRepository);
    }

    @Test
    void findAll() {
        UUID userId = UUID.randomUUID();
        List<Subscription> expectedSubscriptions = buildExpectedSubscriptions(userId);
        when(subscriptionRepository.findAllByUserIdAndDeleted(userId, false))
                .thenReturn(expectedSubscriptions);

        List<Subscription> actualSubscriptions = subscriptionsService.findAll(userId);

        assertThat(actualSubscriptions)
                .isEqualTo(expectedSubscriptions);
    }

    @Test
    void findAllReturnEmptyList() {
        UUID userId = UUID.randomUUID();
        when(subscriptionRepository.findAllByUserIdAndDeleted(userId, false))
                .thenReturn(List.of());

        SubscriptionNotFoundException subscriptionNotFoundException = assertThrows(
                SubscriptionNotFoundException.class,
                () -> subscriptionsService.findAll(userId)
        );

        assertThat(subscriptionNotFoundException.getMessage())
                .isEqualTo("Подписки не найдены");
    }

    @Test
    void subscribe() {
        UUID userId = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId);
        when(subscriptionRepository.save(expectedSubscription))
                .thenReturn(expectedSubscription);

        assertDoesNotThrow(() -> subscriptionsService.subscribe(userId, expectedSubscription));
    }

    @Test
    void subscribeWithDifferentId() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId1);

        SubscriptionBadRequestException subscriptionBadRequestException = assertThrows(
                SubscriptionBadRequestException.class,
                () -> subscriptionsService.subscribe(userId2, expectedSubscription)
        );
        assertThat(subscriptionBadRequestException.getMessage())
                .isEqualTo("id пользователя указан некорректно. id url: " + userId2 + ", id в теле запроса: " + userId1);

        verifyNoInteractions(subscriptionRepository);
    }

    @Test
    void update() {
        UUID userId = UUID.randomUUID();
        UUID subscriptionId = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId, subscriptionId);
        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.of(expectedSubscription));
        when(subscriptionRepository.save(expectedSubscription))
                .thenReturn(expectedSubscription);

        assertDoesNotThrow(() -> subscriptionsService.update(userId, subscriptionId, expectedSubscription));
    }

    @Test
    void updateWithDifferentSubscriptionId() {
        UUID userId = UUID.randomUUID();
        UUID subscriptionId1 = UUID.randomUUID();
        UUID subscriptionId2 = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId, subscriptionId2);

        SubscriptionBadRequestException subscriptionBadRequestException = assertThrows(
                SubscriptionBadRequestException.class,
                () -> subscriptionsService.update(userId, subscriptionId1, expectedSubscription)
        );
        assertThat(subscriptionBadRequestException.getMessage())
                .isEqualTo("id подписки указан некорректно. id url: " + subscriptionId1 + ", id в теле запроса: " + subscriptionId2);

        verifyNoInteractions(subscriptionRepository);
    }

    @Test
    void updateWithDifferentUserId() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID subscriptionId = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId2, subscriptionId);

        SubscriptionBadRequestException subscriptionBadRequestException = assertThrows(
                SubscriptionBadRequestException.class,
                () -> subscriptionsService.update(userId1, subscriptionId, expectedSubscription)
        );
        assertThat(subscriptionBadRequestException.getMessage())
                .isEqualTo("id пользователя указан некорректно. id url: " + userId1 + ", id в теле запроса: " + userId2);

        verifyNoInteractions(subscriptionRepository);
    }

    @Test
    void updateReturnEmptyList() {
        UUID userId = UUID.randomUUID();
        UUID subscriptionId = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId, subscriptionId);

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.empty());

        SubscriptionNotFoundException subscriptionNotFoundException = assertThrows(
                SubscriptionNotFoundException.class,
                () -> subscriptionsService.update(userId, subscriptionId, expectedSubscription)
        );
        assertThat(subscriptionNotFoundException.getMessage())
                .isEqualTo("Подписка с id: " + subscriptionId + " - не найдена");

        verifyNoMoreInteractions(subscriptionRepository);
    }

    @Test
    void unsubscribe() {
        UUID userId = UUID.randomUUID();
        UUID subscriptionId = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId, subscriptionId);
        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.of(expectedSubscription));

        assertDoesNotThrow(() -> subscriptionsService.unsubscribe(userId, subscriptionId));
    }

    @Test
    void unsubscribeAgain() {
        UUID userId = UUID.randomUUID();
        UUID subscriptionId = UUID.randomUUID();
        Subscription expectedSubscription = buildExpectedSubscription(userId, subscriptionId, true);
        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.of(expectedSubscription));

        assertDoesNotThrow(() -> subscriptionsService.unsubscribe(userId, subscriptionId));

        verifyNoMoreInteractions(subscriptionRepository);
    }

    @Test
    void unsubscribeReturnEmptyList() {
        UUID userId = UUID.randomUUID();
        UUID subscriptionId = UUID.randomUUID();
        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.empty());

        SubscriptionNotFoundException subscriptionNotFoundException = assertThrows(
                SubscriptionNotFoundException.class,
                () -> subscriptionsService.unsubscribe(userId, subscriptionId)
        );
        assertThat(subscriptionNotFoundException.getMessage())
                .isEqualTo("Подписка с id: " + subscriptionId + " - не найдена");

        verifyNoMoreInteractions(subscriptionRepository);
    }

    private List<Subscription> buildExpectedSubscriptions(UUID userId) {
        Subscription subscription = buildExpectedSubscription(userId);
        return List.of(subscription);
    }

    private Subscription buildExpectedSubscription(UUID userId) {
        return buildExpectedSubscription(userId, UUID.randomUUID());
    }

    private Subscription buildExpectedSubscription(UUID userId, UUID subscriptionId) {
        return buildExpectedSubscription(userId, subscriptionId, false);
    }

    private Subscription buildExpectedSubscription(UUID userId, UUID subscriptionId, boolean isDeleted) {
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setUserId(userId);
        subscription.setUserSubscriberId(UUID.randomUUID());
        subscription.setDeleted(isDeleted);
        subscription.setLastModifyTime(LocalDateTime.now());
        subscription.setCreateTime(LocalDateTime.now());
        return subscription;
    }
}