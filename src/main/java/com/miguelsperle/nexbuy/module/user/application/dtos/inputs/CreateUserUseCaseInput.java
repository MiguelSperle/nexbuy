package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

public record CreateUserUseCaseInput(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        PersonType personType,
        PersonComplementInput personComplementInput
) {
    public static CreateUserUseCaseInput with(
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            PersonType personType,
            PersonComplementInput personComplementInput
    ) {
        return new CreateUserUseCaseInput(
                firstName,
                lastName,
                email,
                password,
                phoneNumber,
                personType,
                personComplementInput
        );
    }
}

