package ru.social_network.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private UUID userSubscriberId;
    private Boolean deleted;
    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;
}