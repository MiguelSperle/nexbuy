package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserPasswordResetCode {
    private final String id;
    private final User user;
    private final String code;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private UserPasswordResetCode(
            String id,
            User user,
            String code,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.user = user;
        this.code = code;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static UserPasswordResetCode newUserPasswordResetCode(
            User user,
            String code
    ) {
        return new UserPasswordResetCode(
                UUID.randomUUID().toString(),
                user,
                code,
                LocalDateTime.now().plusMinutes(15),
                LocalDateTime.now()
        );
    }

    public static UserPasswordResetCode with(
            String id,
            User user,
            String code,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new UserPasswordResetCode(
                id,
                user,
                code,
                expiresIn,
                createdAt
        );
    }
}
