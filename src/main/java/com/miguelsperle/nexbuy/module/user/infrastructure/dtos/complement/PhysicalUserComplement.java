package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.complement;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalUserComplement {
    @NotNull(message = "Cpf cannot be null")
    @CPF(message = "Cpf must be valid")
    private String cpf;

    @NotBlank(message = "General register cannot be null or empty")
    @Size(max = 15, message = "General register cannot exceed 15 characters")
    private String generalRegister;
}
