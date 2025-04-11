package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserVerificationCode {
    private String id;
    private String code;
    private User user;
    private LocalDateTime expiresIn;

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
