package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank(message = "First name should not be neither null nor blank")
        @Size(max = 50, message = "First name should not exceed 50 characters")
        String firstName,

        @NotBlank(message = "Last name should not be neither null nor blank")
        @Size(max = 50, message = "Last name should not exceed 50 characters")
        String lastName,

        @NotBlank(message = "Phone number should not be neither null nor blank")
        @Pattern(regexp = "^(|\\(\\d{2}\\) \\d{5}-\\d{4})$", message = "Phone number should be in the format (XX) XXXXX-XXXX")
        String phoneNumber
) {
}

