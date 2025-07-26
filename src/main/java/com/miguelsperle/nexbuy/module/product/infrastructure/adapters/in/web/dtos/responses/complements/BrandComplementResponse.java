package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.complements;

public record BrandComplementResponse(
        String name
) {
    public static BrandComplementResponse from(String name) {
        return new BrandComplementResponse(name);
    }
}
