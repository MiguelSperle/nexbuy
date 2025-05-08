package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
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
                LocalDateTime.now().plusDays(30),
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
}
