package com.miguelsperle.nexbuy.module.product.infrastructure.rest.dtos.res.complements;

public record BrandComplementResponse(
        String name
) {
    public static BrandComplementResponse from(String name) {
        return new BrandComplementResponse(name);
    }
}
