package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests.complements;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderDeliveryComplementRequest(
        @NotNull(message = "Address should not be null")
        @Valid
        AddressComplementRequest address,

        @NotNull(message = "Freight should not be null")
        @Valid
        FreightComplementRequest freight
) {
}
