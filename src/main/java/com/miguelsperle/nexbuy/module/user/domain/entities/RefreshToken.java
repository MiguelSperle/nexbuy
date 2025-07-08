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
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", createdAt=" + createdAt +
                '}';
    }
}
