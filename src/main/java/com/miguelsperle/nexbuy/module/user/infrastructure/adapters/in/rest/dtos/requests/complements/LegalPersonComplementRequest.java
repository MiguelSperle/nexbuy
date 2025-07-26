package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests.complements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record LegalPersonComplementRequest(
        @NotNull(message = "Cnpj should not be null")
        @CNPJ(message = "Cnpj should be valid")
        String cnpj,

        @NotBlank(message = "Fantasy name should not be neither null nor blank")
        @Size(max = 50, message = "Fantasy name should not exceed 50 characters")
        String fantasyName,

        @NotBlank(message = "Legal name should not be neither null nor blank")
        @Size(max = 50, message = "Legal name should not exceed 50 characters")
        String legalName,

        @Size(max = 25, message = "State registration should not exceed 25 characters")
        String stateRegistration
) {
}
