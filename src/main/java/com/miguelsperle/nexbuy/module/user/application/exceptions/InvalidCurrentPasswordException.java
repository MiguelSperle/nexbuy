package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class InvalidCurrentPasswordException extends RuntimeException {
    public InvalidCurrentPasswordException(String message) {
        super(message);
    }

    public static InvalidCurrentPasswordException with(String message) {
        return new InvalidCurrentPasswordException(message);
    }
}
