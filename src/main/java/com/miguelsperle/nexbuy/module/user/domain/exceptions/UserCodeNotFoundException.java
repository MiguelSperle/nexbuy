package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class UserCodeNotFoundException extends RuntimeException {
    public UserCodeNotFoundException(String message) {
        super(message);
    }

    public static UserCodeNotFoundException with(String message) {
        return new UserCodeNotFoundException(message);
    }
}
