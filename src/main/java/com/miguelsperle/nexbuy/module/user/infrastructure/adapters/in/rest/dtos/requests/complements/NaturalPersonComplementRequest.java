package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests.complements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record NaturalPersonComplementRequest(
        @NotNull(message = "Cpf should not be null")
        @CPF(message = "Cpf should be valid")
        String cpf,

        @NotBlank(message = "General register should not be neither null nor blank")
        @Size(max = 15, message = "General register should not exceed 15 characters")
        String generalRegister
) {
}

