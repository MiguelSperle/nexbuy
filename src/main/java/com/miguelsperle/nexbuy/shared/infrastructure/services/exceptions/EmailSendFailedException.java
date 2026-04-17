package com.miguelsperle.nexbuy.shared.infrastructure.services.exceptions;

public class EmailSendFailedException extends RuntimeException {
    public EmailSendFailedException(String message) {
        super(message);
    }

    public static EmailSendFailedException with(String message) {
        return new EmailSendFailedException(message);
    }
}
