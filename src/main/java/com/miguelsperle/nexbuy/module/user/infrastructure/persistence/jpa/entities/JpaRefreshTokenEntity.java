package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@AllArgsConstructor
@NoArgsConstructor
public class JpaRefreshTokenEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private JpaUserEntity jpaUserEntity;

    @Column(nullable = false, unique = true, length = 36)
    private String token;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaRefreshTokenEntity from(RefreshToken refreshToken) {
        return new JpaRefreshTokenEntity(
                refreshToken.getId(),
                JpaUserEntity.from(refreshToken.getUser()),
                refreshToken.getToken(),
                refreshToken.getExpiresIn(),
                refreshToken.getCreatedAt()
        );
    }

    public RefreshToken toEntity() {
        return RefreshToken.with(
                this.id,
                this.jpaUserEntity.toEntity(),
                this.token,
                this.expiresIn,
                this.createdAt
        );
    }
}
