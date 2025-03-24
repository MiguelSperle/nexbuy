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
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private JpaUserEntity jpaUserEntity;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    public static JpaUserVerificationCodeEntity from(UserVerificationCode userVerificationCode) {
        return new JpaUserVerificationCodeEntity(
                userVerificationCode.getId(),
                userVerificationCode.getCode(),
                JpaUserEntity.from(userVerificationCode.getUser()),
                userVerificationCode.getExpiresIn()
        );
    }

    public UserVerificationCode toEntity() {
        return UserVerificationCode.with(
                this.id,
                this.code,
                this.jpaUserEntity.toEntity(),
                this.expiresIn
        );
    }
}
