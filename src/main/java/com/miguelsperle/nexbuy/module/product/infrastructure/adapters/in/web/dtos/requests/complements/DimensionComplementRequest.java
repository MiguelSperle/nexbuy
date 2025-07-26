package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.complements;

import jakarta.validation.constraints.NotNull;

public record DimensionComplementRequest(
        @NotNull(message = "Height should not be null")
        Integer height,

        @NotNull(message = "Width should not be null")
        Integer width,

        @NotNull(message = "Length should not be null")
        Integer length
) {
}
