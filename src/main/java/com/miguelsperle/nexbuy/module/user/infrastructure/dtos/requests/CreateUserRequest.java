package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import com.miguelsperle.nexbuy.core.infrastructure.annotations.ValidEnum;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.complements.LegalPersonDataRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.complements.NaturalPersonDataRequest;
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

    @NotBlank(message = "Phone number should not be neither null nor empty")
    @Pattern(regexp = "^(|\\(\\d{2}\\) \\d{5}-\\d{4})$", message = "Phone number should be in the format (XX) XXXXX-XXXX")
    private String phoneNumber;

    @ValidEnum(enumClass = PersonType.class, message = "Person type should be either NATURAL_PERSON or LEGAL_PERSON")
    private String personType;

    @Valid
    private NaturalPersonDataRequest naturalPersonData;

    @Valid
    private LegalPersonDataRequest legalPersonData;
}
