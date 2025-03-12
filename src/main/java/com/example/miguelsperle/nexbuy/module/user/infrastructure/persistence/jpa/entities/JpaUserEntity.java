package com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.example.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.example.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.example.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "person_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @Column(name = "profile_photo", nullable = false, length = 50)
    private String profilePhoto;

    @CreationTimestamp
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
                user.getPersonType(),
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
                this.personType,
                this.profilePhoto,
                this.createdAt
        );
    }
}
