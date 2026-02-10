package com.coco.modules.user.infra.persistence;

import com.coco.modules.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "cocouser")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image_url", length = Integer.MAX_VALUE)
    private String imageUrl;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


    public static UserEntity fromDomain(User user) {
        UserEntity entity = new UserEntity();

        entity.id = user.getId();
        entity.login = user.getLogin();
        entity.password = user.getPassword();
        entity.firstName = user.getFirstName();
        entity.lastName = user.getLastName();
        entity.email = user.getEmail();
        entity.imageUrl = user.getImageUrl();
        entity.createdAt = user.getCreatedAt();
        entity.updatedAt = user.getUpdatedAt();

        return entity;
    }

    public User toDomain() {
        User user = new User();

        user.setId(this.id);
        user.setLogin(this.login);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setImageUrl(this.imageUrl);
        user.setCreatedAt(this.createdAt);
        user.setUpdatedAt(this.updatedAt);

        return user;
    }

}
