package com.miguelsperle.nexbuy.module.inventory.domain.exceptions;

public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(String message) {
        super(message);
    }

    public static InsufficientInventoryException with(String message) {
        return new InsufficientInventoryException(message);
    }
}
