package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserCode {
    private final String id;
    private final String userId;
    private final String code;
    private final CodeType codeType;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private UserCode(
            String id,
            String userId,
            String code,
            CodeType codeType,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.codeType = codeType;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static UserCode newUserCode(
            String userId,
            String code,
            CodeType codeType
    ) {
        return new UserCode(
                UUID.randomUUID().toString(),
                userId,
                code,
                codeType,
                LocalDateTime.now().plusMinutes(15),
                LocalDateTime.now()
        );
    }

    public static UserCode with(
            String id,
            String userId,
            String code,
            CodeType codeType,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new UserCode(
                id,
                userId,
                code,
                codeType,
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
                ", userId=" + this.userId +
                ", code='" + this.code + '\'' +
                ", codeType=" + this.codeType +
                ", expiresIn=" + this.expiresIn +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
