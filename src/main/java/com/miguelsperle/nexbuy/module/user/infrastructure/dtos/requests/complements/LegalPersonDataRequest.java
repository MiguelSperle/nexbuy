package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.complements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalPersonDataRequest {
    @NotNull(message = "Cnpj should not be null")
    @CNPJ(message = "Cnpj should be valid")
    private String cnpj;

    @NotBlank(message = "Fantasy name should not be neither null nor empty")
    @Size(max = 50, message = "Fantasy name should not exceed 50 characters")
    private String fantasyName;

    @NotBlank(message = "Legal name should not be neither null nor empty")
    @Size(max = 50, message = "Legal name should not exceed 50 characters")
    private String legalName;

    @Size(max = 25, message = "State registration should not exceed 25 characters")
    private String stateRegistration;
}
