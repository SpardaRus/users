package ru.social_network.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    private UUID id;
    private UUID userId;
    private UUID userSubscriberId;
    private Boolean deleted;
    private OffsetDateTime createTime;
}