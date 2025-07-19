package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.PersonComplementOutput;

public record NaturalPersonComplementResponse(
        String cpf,
        String generalRegister
) {
    public static NaturalPersonComplementResponse from(PersonComplementOutput personComplementOutput) {
        return new NaturalPersonComplementResponse(
                personComplementOutput.cpf(),
                personComplementOutput.generalRegister()
        );
    }
}

