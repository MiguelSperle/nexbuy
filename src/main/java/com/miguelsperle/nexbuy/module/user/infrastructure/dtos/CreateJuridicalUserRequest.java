package com.miguelsperle.nexbuy.module.user.infrastructure.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateJuridicalUserRequest extends CreateUserRequest {
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
