package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException with(String message) {
        return new UserNotFoundException(message);
    }
}
