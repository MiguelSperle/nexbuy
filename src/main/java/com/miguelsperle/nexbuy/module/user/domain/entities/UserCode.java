package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserCode {
    private final String id;
    private final User user;
    private final String code;
    private final CodeType codeType;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private UserCode(
            String id,
            User user,
            String code,
            CodeType codeType,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.user = user;
        this.code = code;
        this.codeType = codeType;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static UserCode newUserCode(
            User user,
            String code,
            CodeType codeType
    ) {
        return new UserCode(
                UUID.randomUUID().toString(),
                user,
                code,
                codeType,
                LocalDateTime.now().plusMinutes(15),
                LocalDateTime.now()
        );
    }

    public static UserCode with(
            String id,
            User user,
            String code,
            CodeType codeType,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new UserCode(
                id,
                user,
                code,
                codeType,
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

    public String getCode() {
        return this.code;
    }

    public CodeType getCodeType() {
        return this.codeType;
    }

    public LocalDateTime getExpiresIn() {
        return this.expiresIn;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "UserCode{" +
                "id='" + this.id + '\'' +
                ", user=" + this.user +
                ", code='" + this.code + '\'' +
                ", codeType=" + this.codeType +
                ", expiresIn=" + this.expiresIn +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
