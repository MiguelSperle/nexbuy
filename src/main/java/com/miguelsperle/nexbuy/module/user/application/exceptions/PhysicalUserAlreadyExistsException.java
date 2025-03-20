package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class PhysicalUserAlreadyExistsException extends RuntimeException {
    public PhysicalUserAlreadyExistsException(String message) {
        super(message);
    }
}
