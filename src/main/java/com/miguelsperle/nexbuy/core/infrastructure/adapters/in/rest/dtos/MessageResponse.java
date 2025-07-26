package com.miguelsperle.nexbuy.core.infrastructure.adapters.in.rest.dtos;

public record MessageResponse(
        String message
) {
    public static MessageResponse from(String message) {
        return new MessageResponse(message);
    }
}