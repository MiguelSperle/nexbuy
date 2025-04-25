package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserVerificationCode {
    private final String id;
    private final String code;
    private final User user;
    private final LocalDateTime expiresIn;

    private UserVerificationCode(
            String id,
            String code,
            User user,
            LocalDateTime expiresIn
    ) {
        this.id = id;
        this.code = code;
        this.user = user;
        this.expiresIn = expiresIn;
    }

    public static UserVerificationCode newUserVerificationCode(
            String code,
            User user,
            LocalDateTime expiresIn
    ) {
        return new UserVerificationCode(
                UUID.randomUUID().toString(),
                code,
                user,
                expiresIn
        );
    }

    public static UserVerificationCode with(
            String id,
            String code,
            User user,
            LocalDateTime expiresIn
    ) {
        return new UserVerificationCode(
                id,
                code,
                user,
                expiresIn
        );
    }
}
