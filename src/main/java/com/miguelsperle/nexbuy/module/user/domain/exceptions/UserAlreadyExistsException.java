package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException with(String message) {
        return new UserAlreadyExistsException(message);
    }
}
