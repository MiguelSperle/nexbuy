package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ProductBrandAlreadyExistsException extends RuntimeException {
    public ProductBrandAlreadyExistsException(String message) {
        super(message);
    }
}
