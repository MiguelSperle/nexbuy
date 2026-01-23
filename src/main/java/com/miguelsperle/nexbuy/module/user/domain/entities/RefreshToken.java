package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class RefreshToken {
    private final String id;
    private final String userId;
    private final String token;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private RefreshToken(
            String id,
            String userId,
            String token,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static RefreshToken newRefreshToken(
            String userId
    ) {
        return new RefreshToken(
                IdentifierUtils.generateUUID(),
                userId,
                IdentifierUtils.generateUUID(),
                TimeUtils.now().plusDays(15),
                TimeUtils.now()
        );
    }

    public static RefreshToken with(
            String id,
            String userId,
            String token,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new RefreshToken(
                id,
                userId,
                token,
                expiresIn,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getToken() {
        return this.token;
    }

    public LocalDateTime getExpiresIn() {
        return this.expiresIn;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id='" + this.id + '\'' +
                ", userId=" + this.userId +
                ", token='" + this.token + '\'' +
                ", expiresIn=" + this.expiresIn +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
