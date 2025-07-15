package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests;

import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.complements.DimensionComplementRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record RegisterProductRequest(
        @NotBlank(message = "Name should not be neither null nor blank")
        @Size(max = 150, message = "Name should not exceed 150 characters")
        String name,

        @NotBlank(message = "Description should not be neither null nor blank")
        String description,

        @NotBlank(message = "Category id should not be neither null nor blank")
        String categoryId,

        @NotNull(message = "Price should not be null")
        @DecimalMin(value = "0", message = "Price should be at least 0")
        @Digits(integer = 17, fraction = 2, message = "Price should have up to 17 digits before the decimal point and 2 after")
        BigDecimal price,

        @NotBlank(message = "Brand id should not be neither null nor blank")
        String brandId,

        @NotBlank(message = "Color id should not be neither null nor blank")
        String colorId,

        @NotNull(message = "Weight should not be null")
        Integer weight,

        @Valid
        @NotNull(message = "Dimension complement should not be null")
        DimensionComplementRequest dimensionComplement
) {
}
