package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests;

import com.miguelsperle.nexbuy.core.infrastructure.annotations.ValidEnum;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests.complements.LegalPersonComplementRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests.complements.NaturalPersonComplementRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank(message = "First name should not be neither null nor blank")
        @Size(max = 50, message = "First name should not exceed 50 characters")
        String firstName,

        @NotBlank(message = "Last name should not be neither null nor blank")
        @Size(max = 50, message = "Last name should not exceed 50 characters")
        String lastName,

        @NotBlank(message = "Email should not be neither null nor blank")
        @Size(max = 255, message = "Email should not exceed 255 characters")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Password should not be null")
        @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
        String password,

        @NotBlank(message = "Phone number should not be neither null nor blank")
        @Pattern(regexp = "^(|\\(\\d{2}\\) \\d{5}-\\d{4})$", message = "Phone number should be in the format (XX) XXXXX-XXXX")
        String phoneNumber,

        @ValidEnum(enumClass = PersonType.class, message = "Person type should be either NATURAL_PERSON or LEGAL_PERSON")
        String personType,

        @Valid
        NaturalPersonComplementRequest naturalPerson,

        @Valid
        LegalPersonComplementRequest legalPerson
) {
}

