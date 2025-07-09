package com.miguelsperle.nexbuy.module.user.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshToken {
    private final String id;
    private final User user;
    private final String token;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private RefreshToken(
            String id,
            User user,
            String token,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static RefreshToken newRefreshToken(
            User user
    ) {
        return new RefreshToken(
                UUID.randomUUID().toString(),
                user,
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusDays(15),
                LocalDateTime.now()
        );
    }

    public static RefreshToken with(
            String id,
            User user,
            String token,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new RefreshToken(
                id,
                user,
                token,
                expiresIn,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
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
                ", user=" + this.user +
                ", token='" + this.token + '\'' +
                ", expiresIn=" + this.expiresIn +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
