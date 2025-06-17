package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.LegalPersonComplementResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAuthenticatedUserLegalPersonResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AuthorizationRole authorizationRole;
    private PersonType personType;
    private LegalPersonComplementResponse legalPersonComplement;

    public static GetAuthenticatedUserLegalPersonResponse fromOutput(GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput) {
        return new GetAuthenticatedUserLegalPersonResponse(
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getFirstName(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getLastName(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getEmail(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getPersonType(),
                LegalPersonComplementResponse.fromOutput(getAuthenticatedUserUseCaseOutput.getPersonComplementOutput())
        );
    }
}
