package ru.social_network.users.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.social_network.users.entity.Subscription;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Transactional
public interface SubscriptionRepository extends CrudRepository<Subscription, UUID> {

    List<Subscription> findAllByUserIdAndDeleted(UUID userId, Boolean deleted);

    @Modifying
    @Query("UPDATE Subscription subscriptions " +
            " SET subscriptions.deleted = true, " +
            " subscriptions.lastModifyTime = :lastModifyTime " +
            " WHERE subscriptions.id = :subscriptionsId")
    void softDelete(UUID subscriptionsId, LocalDateTime lastModifyTime);
}
