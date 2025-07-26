package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.complements;

public record CategoryComplementResponse(
        String name
) {
    public static CategoryComplementResponse from(String name) {
        return new CategoryComplementResponse(name);
    }
}
