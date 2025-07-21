package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.complements.DimensionComplementInput;

import java.math.BigDecimal;

public record RegisterProductUseCaseInput(
        String name,
        String description,
        String categoryId,
        BigDecimal price,
        String brandId,
        String colorId,
        Integer weight,
        DimensionComplementInput dimensionComplementInput
) {
    public static RegisterProductUseCaseInput with(
            String name,
            String description,
            String categoryId,
            BigDecimal price,
            String brandId,
            String colorId,
            Integer weight,
            DimensionComplementInput dimensionComplementInput
    ) {
        return new RegisterProductUseCaseInput(
                name,
                description,
                categoryId,
                price,
                brandId,
                colorId,
                weight,
                dimensionComplementInput
        );
    }
}
