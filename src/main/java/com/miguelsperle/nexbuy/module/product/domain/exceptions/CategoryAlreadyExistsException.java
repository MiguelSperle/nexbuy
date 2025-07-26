package com.miguelsperle.nexbuy.module.product.domain.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public static CategoryAlreadyExistsException with(String message) {
        return new CategoryAlreadyExistsException(message);
    }
}
