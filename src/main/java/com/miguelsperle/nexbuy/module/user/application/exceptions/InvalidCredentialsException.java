package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public static InvalidCredentialsException with(String message) {
        return new InvalidCredentialsException(message);
    }
}
