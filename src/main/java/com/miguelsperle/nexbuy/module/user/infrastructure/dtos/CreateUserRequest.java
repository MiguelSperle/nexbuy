package com.miguelsperle.nexbuy.module.user.infrastructure.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "First name cannot be null or empty")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or empty")
    @Size(max = 50, message = "Last name cannot be null or empty")
    private String lastName;

    @NotBlank(message = "Email cannot be empty or null")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    @Email(message = "Email must contain a valid format")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 5, max = 100, message = "Password must contain between 5 and 100 characters")
    private String password;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Phone number must be in the format (XX) XXXXX-XXXX")
    private String phoneNumber;
}
