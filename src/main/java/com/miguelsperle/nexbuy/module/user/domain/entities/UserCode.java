package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;

import java.time.LocalDateTime;

public class UserCode {
    private final String id;
    private final String userId;
    private final String code;
    private final UserCodeType userCodeType;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private UserCode(
            String id,
            String userId,
            String code,
            UserCodeType userCodeType,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.userCodeType = userCodeType;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static UserCode newUserCode(
            String userId,
            String code,
            UserCodeType userCodeType
    ) {
        return new UserCode(
                IdentifierUtils.generateUUID(),
                userId,
                code,
                userCodeType,
                TimeUtils.now().plusMinutes(15),
                TimeUtils.now()
        );
    }

    public static UserCode with(
            String id,
            String userId,
            String code,
            UserCodeType userCodeType,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new UserCode(
                id,
                userId,
                code,
                userCodeType,
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

    public UserCodeType getUserCodeType() {
        return this.userCodeType;
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
                ", userCodeType=" + this.userCodeType +
                ", expiresIn=" + this.expiresIn +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
