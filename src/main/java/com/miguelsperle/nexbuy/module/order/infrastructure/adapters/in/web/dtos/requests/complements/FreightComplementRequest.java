package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests.complements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FreightComplementRequest(
        @NotBlank(message = "Name should not neither be null nor blank")
        String name,

        @NotBlank(message = "Company name should not neither be null nor blank")
        String companyName,

        @NotNull(message = "Price should not be null")
        @Positive(message = "Price should be a positive number")
        BigDecimal price,

        @NotNull(message = "Estimated time should not be null")
        @Positive(message = "Estimated time should be a positive number")
        Integer estimatedTime,

        @NotNull(message = "Min time should not be null")
        @Positive(message = "Min time should be a positive number")
        Integer minTime,

        @NotNull(message = "Max time should not be null")
        @Positive(message = "Max time should be a positive number")
        Integer maxTime
) {
}
