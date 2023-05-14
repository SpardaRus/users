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
@Table(name = "users")
public class User {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String info;
    private Boolean deleted;
    private OffsetDateTime createTime;
    private OffsetDateTime lastModifyTime;
}
