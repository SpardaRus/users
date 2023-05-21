package ru.social_network.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.social_network.users.entity.Subscription;
import ru.social_network.users.exception.subscription.SubscriptionBadRequestException;
import ru.social_network.users.exception.subscription.SubscriptionNotFoundException;
import ru.social_network.users.repository.SubscriptionRepository;
import ru.social_network.users.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SubscriptionsService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> findAll(UUID userId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserIdAndDeleted(userId, false);
        if (subscriptions.isEmpty()) {
            throw new SubscriptionNotFoundException("Подписки не найдены");
        }
        return subscriptions;
    }

    public void subscribe(UUID userId, Subscription subscription) {
        verifyIdOrThrow(userId, subscription);

        LocalDateTime now = DateUtils.now();
        subscription.setCreateTime(now);
        subscription.setLastModifyTime(now);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Подписка для пользователя {} - успешно создана с id: {}", userId, savedSubscription.getId());
    }

    @Transactional
    public void update(UUID userId, UUID subscriptionsId, Subscription subscription) {
        verifyIdOrThrow(userId, subscriptionsId, subscription);

        subscriptionRepository.findById(subscriptionsId)
                .ifPresentOrElse(
                        foundSubscription -> {
                            subscription.setLastModifyTime(DateUtils.now());
                            subscription.setCreateTime(foundSubscription.getCreateTime());
                            subscriptionRepository.save(subscription);
                            log.info("Подписка для пользователя {} - успешно обновлена с id: {}", userId, subscription.getId());
                        },
                        () -> {
                            throw new SubscriptionNotFoundException(subscriptionsId);
                        }
                );
    }

    @Transactional
    public void unsubscribe(UUID userId, UUID subscriptionsId) {
        subscriptionRepository.findById(subscriptionsId)
                .ifPresentOrElse(
                        foundSubscription -> {
                            if (Boolean.TRUE.equals(foundSubscription.getDeleted())) {
                                log.warn("Подписка: {} - уже удалена", foundSubscription.getId());
                            } else {
                                subscriptionRepository.softDelete(subscriptionsId, DateUtils.now());
                                log.info("Подписка {} - успешно удалена для пользователя {}", subscriptionsId, userId);
                            }
                        },
                        () -> {
                            throw new SubscriptionNotFoundException(subscriptionsId);
                        }
                );
    }

    private void verifyIdOrThrow(UUID userId, Subscription subscription) {
        if (!userId.equals(subscription.getUserId())) {
            throw new SubscriptionBadRequestException("id пользователя указан некорректно. id url: " + userId + ", id в теле запроса: " + subscription.getUserId());
        }
    }

    private void verifyIdOrThrow(UUID userId, UUID subscriptionsId, Subscription subscription) {
        verifyIdOrThrow(userId, subscription);

        if (!subscriptionsId.equals(subscription.getId())) {
            throw new SubscriptionBadRequestException("id подписки указан некорректно. id url: " + subscriptionsId + ", id в теле запроса: " + subscription.getId());
        }
    }
}
