package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.complements;

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
public class JuridicalUserComplement {
    @NotNull(message = "Cnpj cannot be null")
    @CNPJ(message = "Cnpj must be valid")
    private String cnpj;

    @NotBlank(message = "Fantasy name cannot be null or empty")
    @Size(max = 50, message = "Fantasy name cannot exceed 50 characters")
    private String fantasyName;

    @NotBlank(message = "Legal name cannot be null or empty")
    @Size(max = 50, message = "Legal name cannot exceed 50 characters")
    private String legalName;

    @Size(max = 25, message = "State registration cannot exceed 25 characters")
    private String stateRegistration;
}
