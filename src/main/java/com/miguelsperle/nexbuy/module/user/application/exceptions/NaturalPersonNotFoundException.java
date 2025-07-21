package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class NaturalPersonNotFoundException extends RuntimeException {
    public NaturalPersonNotFoundException(String message) {
        super(message);
    }

    public static NaturalPersonNotFoundException with(String message) {
        return new NaturalPersonNotFoundException(message);
    }
}
