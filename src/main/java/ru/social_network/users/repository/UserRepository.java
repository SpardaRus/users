package ru.social_network.users.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.social_network.users.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    List<User> findAll();

    @Query("SELECT user FROM User user " +
            " JOIN Subscription subscriptions ON subscriptions.userSubscriberId =user.id" +
            " WHERE subscriptions.userId=:id")
    List<User> findSubscriptionsByUserId(UUID id);
}
