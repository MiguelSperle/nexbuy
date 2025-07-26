package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.complements;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.complements.PersonComplementOutput;

public record LegalPersonComplementResponse(
        String cnpj,
        String fantasyName,
        String legalName,
        String stateRegistration
) {
    public static LegalPersonComplementResponse from(PersonComplementOutput personComplementOutput) {
        return new LegalPersonComplementResponse(
                personComplementOutput.cnpj(),
                personComplementOutput.fantasyName(),
                personComplementOutput.legalName(),
                personComplementOutput.stateRegistration()
        );
    }
}

