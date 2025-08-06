package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.complements;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DimensionComplementRequest(
        @NotNull(message = "Height should not be null")
        @Positive(message = "Height should be a positive number")
        Integer height,

        @NotNull(message = "Width should not be null")
        @Positive(message = "Width should be a positive number")
        Integer width,

        @NotNull(message = "Length should not be null")
        @Positive(message = "Length should be a positive number")
        Integer length
) {
}
