package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.complements;

public record DimensionComplementInput(
        Integer height,
        Integer width,
        Integer length
) {
    public static DimensionComplementInput with(Integer height, Integer width, Integer length) {
        return new DimensionComplementInput(height, width, length);
    }
}
