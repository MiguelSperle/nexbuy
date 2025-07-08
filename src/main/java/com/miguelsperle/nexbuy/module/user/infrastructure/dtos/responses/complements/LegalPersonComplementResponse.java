package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.PersonComplementOutput;

public record LegalPersonComplementResponse(
        String cnpj,
        String fantasyName,
        String legalName,
        String stateRegistration
) {
    public static LegalPersonComplementResponse fromOutput(PersonComplementOutput personComplementOutput) {
        return new LegalPersonComplementResponse(
                personComplementOutput.cnpj(),
                personComplementOutput.fantasyName(),
                personComplementOutput.legalName(),
                personComplementOutput.stateRegistration()
        );
    }
}

