package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.requests.complement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductsComplementRequest(
        @NotBlank(message = "Id should not be neither null nor blank")
        String id,

        @NotNull(message = "Height should not be null")
        @Positive(message = "Height should be positive")
        Integer height,

        @NotNull(message = "Width should not be null")
        @Positive(message = "Width should be positive")
        Integer width,

        @NotNull(message = "Length should not be null")
        @Positive(message = "Length should be positive")
        Integer length,

        @NotNull(message = "Weight should not be null")
        @Positive(message = "Weight should be positive")
        Integer weight,

        @NotNull(message = "Quantity should not be null")
        @Positive(message = "Quantity should be positive")
        Integer quantity
) {
}
