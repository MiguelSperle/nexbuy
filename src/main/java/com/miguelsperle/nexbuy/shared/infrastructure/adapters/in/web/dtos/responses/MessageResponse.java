package com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses;

public record MessageResponse(
        String message
) {
    public static MessageResponse from(String message) {
        return new MessageResponse(message);
    }
}