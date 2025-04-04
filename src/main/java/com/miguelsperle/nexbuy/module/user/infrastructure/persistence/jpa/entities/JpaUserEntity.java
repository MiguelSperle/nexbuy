package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaUserEntity {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Column(name = "authorization_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorizationRole authorizationRole;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "profile_photo", nullable = false, length = 50)
    private String profilePhoto;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaUserEntity from(User user) {
        return new JpaUserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getIsVerified(),
                user.getAuthorizationRole(),
                user.getUserType(),
                user.getProfilePhoto(),
                user.getCreatedAt()
        );
    }

    public User toEntity() {
        return User.with(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.password,
                this.phoneNumber,
                this.isVerified,
                this.authorizationRole,
                this.userType,
                this.profilePhoto,
                this.createdAt
        );
    }
}
