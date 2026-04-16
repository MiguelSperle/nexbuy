package com.miguelsperle.nexbuy.module.user.infrastructure.rest.dtos.res.complements;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.complements.PersonComplementOutput;

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

