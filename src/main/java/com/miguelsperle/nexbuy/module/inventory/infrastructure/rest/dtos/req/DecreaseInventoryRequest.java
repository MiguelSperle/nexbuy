package com.miguelsperle.nexbuy.module.inventory.infrastructure.rest.dtos.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DecreaseInventoryRequest(
        @NotNull(message = "Quantity should not be null")
        @Positive(message = "Quantity should be positive number")
        Integer quantity
) {
}
