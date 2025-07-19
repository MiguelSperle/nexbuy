package com.miguelsperle.nexbuy.core.infrastructure.dtos;

public record MessageResponse(
        String message
) {
    public static MessageResponse from(String message) {
        return new MessageResponse(message);
    }
}