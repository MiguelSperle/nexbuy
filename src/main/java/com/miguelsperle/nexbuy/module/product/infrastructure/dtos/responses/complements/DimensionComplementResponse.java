package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements;

public record DimensionComplementResponse(
        Integer height,
        Integer width,
        Integer length
) {
    public static DimensionComplementResponse from(Integer height, Integer width, Integer length) {
        return new DimensionComplementResponse(height, width, length);
    }
}
