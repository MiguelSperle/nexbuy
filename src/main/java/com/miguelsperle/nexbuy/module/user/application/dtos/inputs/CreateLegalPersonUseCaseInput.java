package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public record CreateLegalPersonUseCaseInput(
        User user,
        String cnpj,
        String fantasyName,
        String legalName,
        String stateRegistration
) {
    public static CreateLegalPersonUseCaseInput with(
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new CreateLegalPersonUseCaseInput(
                user,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration
        );
    }
}

