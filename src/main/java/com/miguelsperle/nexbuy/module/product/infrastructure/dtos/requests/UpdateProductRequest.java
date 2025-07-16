package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests;

import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.complements.DimensionComplementRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank(message = "Description should not be neither null nor blank")
        String description,

        @NotNull(message = "Price should not be null")
        @PositiveOrZero(message = "Price should be zero or a positive number")
        @Digits(integer = 17, fraction = 2, message = "Price should have up to 17 digits before the decimal point and 2 after")
        BigDecimal price,

        @NotNull(message = "Weight should not be null")
        Integer weight,

        @Valid
        @NotNull(message = "Dimension complement should not be null")
        DimensionComplementRequest dimensionComplement
) {
}
