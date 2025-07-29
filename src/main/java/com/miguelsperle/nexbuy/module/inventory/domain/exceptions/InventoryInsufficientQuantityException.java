package com.miguelsperle.nexbuy.module.inventory.domain.exceptions;

public class InventoryInsufficientQuantityException extends RuntimeException {
    public InventoryInsufficientQuantityException(String message) {
        super(message);
    }

    public static InventoryInsufficientQuantityException with(String message) {
        return new InventoryInsufficientQuantityException(message);
    }
}
