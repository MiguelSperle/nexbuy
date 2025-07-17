package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ProductAlreadyDeletedException extends RuntimeException {
    public ProductAlreadyDeletedException(String message) {
        super(message);
    }
}
