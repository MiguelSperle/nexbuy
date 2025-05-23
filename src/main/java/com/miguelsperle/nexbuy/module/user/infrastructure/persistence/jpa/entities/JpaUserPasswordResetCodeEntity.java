package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserPasswordResetCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_password_reset_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaUserPasswordResetCodeEntity {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private JpaUserEntity jpaUserEntity;

    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaUserPasswordResetCodeEntity from(UserPasswordResetCode userPasswordResetCode) {
        return new JpaUserPasswordResetCodeEntity(
                userPasswordResetCode.getId(),
                JpaUserEntity.from(userPasswordResetCode.getUser()),
                userPasswordResetCode.getCode(),
                userPasswordResetCode.getExpiresIn(),
                userPasswordResetCode.getCreatedAt()
        );
    }

    public UserPasswordResetCode toEntity() {
        return UserPasswordResetCode.with(
                this.id,
                this.jpaUserEntity.toEntity(),
                this.code,
                this.expiresIn,
                this.createdAt
        );
    }
}
