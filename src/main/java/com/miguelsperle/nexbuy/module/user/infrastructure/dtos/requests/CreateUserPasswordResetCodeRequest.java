package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserPasswordResetCodeRequest {
    @NotBlank(message = "Email should not be neither null nor empty")
    @Email(message = "Email should be valid")
    private String email;
}
