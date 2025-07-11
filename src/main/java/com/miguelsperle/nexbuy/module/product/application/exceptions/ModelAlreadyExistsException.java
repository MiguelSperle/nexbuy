package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ModelAlreadyExistsException extends RuntimeException {
    public ModelAlreadyExistsException(String message) {
        super(message);
    }
}
