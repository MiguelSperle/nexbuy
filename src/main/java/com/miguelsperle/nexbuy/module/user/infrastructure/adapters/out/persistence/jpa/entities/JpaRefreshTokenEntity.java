package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.entities;

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

    @Column(name = "user_id", nullable = false, unique = true, length = 36)
    private String userId;

    @Column(nullable = false, unique = true, length = 36)
    private String token;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaRefreshTokenEntity from(RefreshToken refreshToken) {
        return new JpaRefreshTokenEntity(
                refreshToken.getId(),
                refreshToken.getUserId(),
                refreshToken.getToken(),
                refreshToken.getExpiresIn(),
                refreshToken.getCreatedAt()
        );
    }

    public RefreshToken toEntity() {
        return RefreshToken.with(
                this.id,
                this.userId,
                this.token,
                this.expiresIn,
                this.createdAt
        );
    }
}
