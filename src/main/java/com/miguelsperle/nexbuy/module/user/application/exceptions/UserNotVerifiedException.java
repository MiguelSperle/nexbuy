package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class UserNotVerifiedException extends RuntimeException {
    public UserNotVerifiedException(String message) {
        super(message);
    }

    public static UserNotVerifiedException with(String message) {
        return new UserNotVerifiedException(message);
    }
}
