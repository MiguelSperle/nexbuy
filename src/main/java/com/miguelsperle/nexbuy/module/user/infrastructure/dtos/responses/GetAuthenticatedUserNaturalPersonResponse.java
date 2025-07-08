package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.NaturalPersonComplementResponse;

public record GetAuthenticatedUserNaturalPersonResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        AuthorizationRole authorizationRole,
        PersonType personType,
        NaturalPersonComplementResponse naturalPersonComplement
) {
    public static GetAuthenticatedUserNaturalPersonResponse fromOutput(GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput) {
        return new GetAuthenticatedUserNaturalPersonResponse(
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getFirstName(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getLastName(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getEmail(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getPersonType(),
                NaturalPersonComplementResponse.fromOutput(getAuthenticatedUserUseCaseOutput.personComplementOutput())
        );
    }
}
