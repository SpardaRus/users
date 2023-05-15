package ru.social_network.users.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.social_network.users.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface UserRepository extends CrudRepository<User, UUID> {

    @Query("SELECT user FROM User user " +
            " WHERE user.deleted = false")
    List<User> findAll();

    @Query("SELECT user FROM User user " +
            " WHERE user.deleted = false" +
            " AND user.id = :id")
    Optional<User> findById(UUID id);

    @Modifying
    @Query("UPDATE User user " +
            " SET user.deleted = true, " +
            " user.lastModifyTime = :lastModifyTime " +
            " WHERE user.id = :id")
    void delete(UUID id, OffsetDateTime lastModifyTime);
}
