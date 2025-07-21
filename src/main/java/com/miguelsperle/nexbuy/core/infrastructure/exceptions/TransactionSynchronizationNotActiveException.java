package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class TransactionSynchronizationNotActiveException extends RuntimeException {
    public TransactionSynchronizationNotActiveException(String message) {
        super(message);
    }

    public static TransactionSynchronizationNotActiveException with(String message) {
        return new TransactionSynchronizationNotActiveException(message);
    }
}
