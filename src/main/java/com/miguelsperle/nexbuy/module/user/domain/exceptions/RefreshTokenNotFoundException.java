package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }

    public static RefreshTokenNotFoundException with(String message) {
        return new RefreshTokenNotFoundException(message);
    }
}
