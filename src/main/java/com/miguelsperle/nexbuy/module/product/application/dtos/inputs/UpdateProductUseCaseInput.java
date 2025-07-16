package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.complements.DimensionComplementInput;

import java.math.BigDecimal;

public record UpdateProductUseCaseInput(
        String productId,
        String description,
        BigDecimal price,
        Integer weight,
        DimensionComplementInput dimensionComplementInput
) {
}
