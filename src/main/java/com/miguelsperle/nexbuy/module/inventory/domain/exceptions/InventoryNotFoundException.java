package com.miguelsperle.nexbuy.module.inventory.domain.exceptions;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String message) {
        super(message);
    }

    public static InventoryNotFoundException with(String message) {
        return new InventoryNotFoundException(message);
    }
}
