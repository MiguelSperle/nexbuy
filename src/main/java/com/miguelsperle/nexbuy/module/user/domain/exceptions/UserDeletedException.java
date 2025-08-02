package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class UserDeletedException extends RuntimeException {
    public UserDeletedException(String message) {
        super(message);
    }

    public static UserDeletedException with(String message) {
        return new UserDeletedException(message);
    }
}
