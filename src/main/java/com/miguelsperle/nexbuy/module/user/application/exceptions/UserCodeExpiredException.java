package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class UserCodeExpiredException extends RuntimeException {
    public UserCodeExpiredException(String message) {
        super(message);
    }

    public static UserCodeExpiredException with(String message) {
        return new UserCodeExpiredException(message);
    }
}
