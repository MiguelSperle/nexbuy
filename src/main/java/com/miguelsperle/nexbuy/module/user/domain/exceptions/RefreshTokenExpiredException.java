package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }

    public static RefreshTokenExpiredException with(String message) {
        return new RefreshTokenExpiredException(message);
    }
}
