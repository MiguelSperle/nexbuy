package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.complements.DimensionComplementInput;

import java.math.BigDecimal;

public record UpdateProductUseCaseInput(
        String productId,
        String name,
        String description,
        String categoryId,
        BigDecimal price,
        String brandId,
        String colorId,
        Integer weight,
        DimensionComplementInput dimensionComplementInput
) {
    public static UpdateProductUseCaseInput with(
            String productId,
            String name,
            String description,
            String categoryId,
            BigDecimal price,
            String brandId,
            String colorId,
            Integer weight,
            DimensionComplementInput dimensionComplementInput
    ) {
        return new UpdateProductUseCaseInput(
                productId,
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
