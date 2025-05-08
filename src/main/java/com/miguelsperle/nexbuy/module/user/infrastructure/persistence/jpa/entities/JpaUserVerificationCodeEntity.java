package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_verification_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaUserVerificationCodeEntity {
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

    public static JpaUserVerificationCodeEntity from(UserVerificationCode userVerificationCode) {
        return new JpaUserVerificationCodeEntity(
                userVerificationCode.getId(),
                JpaUserEntity.from(userVerificationCode.getUser()),
                userVerificationCode.getCode(),
                userVerificationCode.getExpiresIn(),
                userVerificationCode.getCreatedAt()
        );
    }

    public UserVerificationCode toEntity() {
        return UserVerificationCode.with(
                this.id,
                this.jpaUserEntity.toEntity(),
                this.code,
                this.expiresIn,
                this.createdAt
        );
    }
}
