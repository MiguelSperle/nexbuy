package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.LegalPersonComplementResponse;

public record GetAuthenticatedUserLegalPersonResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        AuthorizationRole authorizationRole,
        PersonType personType,
        LegalPersonComplementResponse legalPersonComplement
) {
    public static GetAuthenticatedUserLegalPersonResponse fromOutput(GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput) {
        return new GetAuthenticatedUserLegalPersonResponse(
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getFirstName(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getLastName(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getEmail(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getPersonType(),
                LegalPersonComplementResponse.fromOutput(getAuthenticatedUserUseCaseOutput.personComplementOutput())
        );
    }
}
