package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements;

public record BrandComplementResponse(
        String name
) {
    public static BrandComplementResponse from(String name) {
        return new BrandComplementResponse(name);
    }
}
