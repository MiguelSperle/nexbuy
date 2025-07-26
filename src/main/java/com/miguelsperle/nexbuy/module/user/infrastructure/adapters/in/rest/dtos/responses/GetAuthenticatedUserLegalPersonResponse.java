package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.responses.complements.LegalPersonComplementResponse;

public record GetAuthenticatedUserLegalPersonResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        AuthorizationRole authorizationRole,
        PersonType personType,
        LegalPersonComplementResponse legalPerson
) {
    public static GetAuthenticatedUserLegalPersonResponse from(GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput) {
        return new GetAuthenticatedUserLegalPersonResponse(
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getFirstName(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getLastName(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getEmail(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.authenticatedUser().getPersonType(),
                LegalPersonComplementResponse.from(getAuthenticatedUserUseCaseOutput.personComplementOutput())
        );
    }
}
