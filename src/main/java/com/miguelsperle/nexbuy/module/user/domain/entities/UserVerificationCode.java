package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserVerificationCode {
    private final String id;
    private final User user;
    private final String code;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private UserVerificationCode(
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

    public static UserVerificationCode newUserVerificationCode(
            User user,
            String code
    ) {
        return new UserVerificationCode(
                UUID.randomUUID().toString(),
                user,
                code,
                LocalDateTime.now().plusMinutes(15),
                LocalDateTime.now()
        );
    }

    public static UserVerificationCode with(
            String id,
            User user,
            String code,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new UserVerificationCode(
                id,
                user,
                code,
                expiresIn,
                createdAt
        );
    }
}
