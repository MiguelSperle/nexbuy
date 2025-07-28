package com.miguelsperle.nexbuy.core.infrastructure.adapters.exceptions;

public class EventProcessingFailureException extends RuntimeException {
    public EventProcessingFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EventProcessingFailureException with(String message, Throwable cause) {
        return new EventProcessingFailureException(message, cause);
    }
}
