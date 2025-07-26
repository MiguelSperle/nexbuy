package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException(String message) {
        super(message);
    }

    public static UserAlreadyVerifiedException with(String message) {
        return new UserAlreadyVerifiedException(message);
    }
}
