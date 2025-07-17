package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ProductStatusNotAllowedException extends RuntimeException {
    public ProductStatusNotAllowedException(String message) {
        super(message);
    }
}
