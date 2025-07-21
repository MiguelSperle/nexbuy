package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class BrandAlreadyExistsException extends RuntimeException {
    public BrandAlreadyExistsException(String message) {
        super(message);
    }

    public static BrandAlreadyExistsException with(String message) {
        return new BrandAlreadyExistsException(message);
    }
}
