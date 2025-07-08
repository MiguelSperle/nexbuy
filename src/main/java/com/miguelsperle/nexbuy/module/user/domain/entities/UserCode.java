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
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCode() {
        return code;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "UserCode{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", code='" + code + '\'' +
                ", codeType=" + codeType +
                ", expiresIn=" + expiresIn +
                ", createdAt=" + createdAt +
                '}';
    }
}
