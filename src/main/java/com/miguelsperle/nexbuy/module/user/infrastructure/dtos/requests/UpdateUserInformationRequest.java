package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInformationRequest {
    @NotBlank(message = "First name should not be neither null nor empty")
    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name should not be neither null nor empty")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Phone number should not be neither null nor empty")
    @Pattern(regexp = "^(|\\(\\d{2}\\) \\d{5}-\\d{4})$", message = "Phone number should be in the format (XX) XXXXX-XXXX")
    private String phoneNumber;
}
