package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.NaturalPersonComplementResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAuthenticatedUserNaturalPersonResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AuthorizationRole authorizationRole;
    private PersonType personType;
    private NaturalPersonComplementResponse naturalPersonComplement;

    public static GetAuthenticatedUserNaturalPersonResponse fromOutput(GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput) {
        return new GetAuthenticatedUserNaturalPersonResponse(
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getFirstName(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getLastName(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getEmail(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getPersonType(),
                NaturalPersonComplementResponse.fromOutput(getAuthenticatedUserUseCaseOutput.getPersonComplementOutput())
        );
    }
}
