package ru.social_network.users.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.social_network.users.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface UserRepository extends CrudRepository<User, UUID> {

    List<User> findAllByDeleted(Boolean deleted);

    Optional<User> findByIdAndDeleted(UUID userId, Boolean deleted);

    @Modifying
    @Query("UPDATE User user " +
            " SET user.deleted = true, " +
            " user.lastModifyTime = :lastModifyTime " +
            " WHERE user.id = :userId")
    void softDelete(UUID userId, LocalDateTime lastModifyTime);
}
