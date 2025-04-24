package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.complements;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalUserComplement {
    @NotNull(message = "Cpf should not be null")
    @CPF(message = "Cpf should be valid")
    private String cpf;

    @NotBlank(message = "General register should not be neither null nor empty")
    @Size(max = 15, message = "General register should not exceed 15 characters")
    private String generalRegister;
}
