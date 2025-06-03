package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetUserPasswordRequest {
    @NotBlank(message = "Code should not be neither null nor empty")
    private String code;

    @NotNull(message = "Password should not be null")
    @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
    private String password;
}
