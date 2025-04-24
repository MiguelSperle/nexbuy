package com.miguelsperle.nexbuy.module.user.infrastructure.dtos;

import com.miguelsperle.nexbuy.core.infrastructure.annotations.ValidEnum;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.complements.JuridicalUserComplement;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.complements.PhysicalUserComplement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "First name should not be neither null nor empty")
    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name should not be neither null nor empty")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email should not be neither null nor empty")
    @Size(max = 255, message = "Email should not exceed 255 characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password should not be null")
    @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
    private String password;

    @NotNull(message = "Phone number should not be null")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Phone number should be in the format (XX) XXXXX-XXXX")
    private String phoneNumber;

    @ValidEnum(enumClass = UserType.class, message = "User type should be either PHYSICAL_USER or JURIDICAL_USER")
    private String userType;

    @Valid
    private PhysicalUserComplement physicalUserComplement;

    @Valid
    private JuridicalUserComplement juridicalUserComplement;
}
