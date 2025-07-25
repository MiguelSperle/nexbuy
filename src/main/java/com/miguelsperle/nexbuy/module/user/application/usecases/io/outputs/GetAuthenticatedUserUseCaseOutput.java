package com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.complements.PersonComplementOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public record GetAuthenticatedUserUseCaseOutput(
        User authenticatedUser,
        PersonComplementOutput personComplementOutput
) {
    public static GetAuthenticatedUserUseCaseOutput from(User authenticatedUser, PersonComplementOutput personComplementOutput) {
        return new GetAuthenticatedUserUseCaseOutput(authenticatedUser, personComplementOutput);
    }
}

