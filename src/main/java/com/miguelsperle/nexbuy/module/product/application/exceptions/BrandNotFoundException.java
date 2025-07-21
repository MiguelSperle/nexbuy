package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(String message) {
        super(message);
    }

    public static BrandNotFoundException with(String message) {
        return new BrandNotFoundException(message);
    }
}
