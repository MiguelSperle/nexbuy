package com.miguelsperle.nexbuy.common.infrastructure.adapters.in.web.dtos.responses;

public record MessageResponse(
        String message
) {
    public static MessageResponse from(String message) {
        return new MessageResponse(message);
    }
}