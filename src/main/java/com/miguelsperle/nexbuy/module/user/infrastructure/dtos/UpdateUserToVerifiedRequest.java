package com.miguelsperle.nexbuy.module.user.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserToVerifiedRequest {
    @NotBlank(message = "Code should not be neither null nor empty")
    private String code;
}
