package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.requests;

import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.requests.complement.ProductsComplementRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ConsultFreightQuotesRequest(
        @NotBlank(message = "To cep should not be neither null nor blank")
        String toCep,

        @NotNull(message = "Products should not be null")
        @Valid
        List<ProductsComplementRequest> products
) {
}
