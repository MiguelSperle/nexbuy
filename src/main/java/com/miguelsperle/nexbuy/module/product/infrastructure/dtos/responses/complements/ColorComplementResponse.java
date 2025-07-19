package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements;

public record ColorComplementResponse(
        String name
) {
    public static ColorComplementResponse from(String name) {
        return new ColorComplementResponse(name);
    }
}
