package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message) {
        super(message);
    }

    public static AddressNotFoundException with(String message) {
        return new AddressNotFoundException(message);
    }
}
