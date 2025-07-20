package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public record CreateLegalPersonUseCaseInput(
        String userId,
        String cnpj,
        String fantasyName,
        String legalName,
        String stateRegistration
) {
    public static CreateLegalPersonUseCaseInput with(
            String userId,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new CreateLegalPersonUseCaseInput(
                userId,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration
        );
    }
}

