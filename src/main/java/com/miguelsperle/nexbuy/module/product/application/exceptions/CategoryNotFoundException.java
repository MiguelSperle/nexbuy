package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public static CategoryNotFoundException with(String message) {
        return new CategoryNotFoundException(message);
    }
}
