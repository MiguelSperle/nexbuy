package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.bases.BaseAuthenticatedUserResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.NaturalPersonComplementResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetAuthenticatedUserNaturalPersonResponse extends BaseAuthenticatedUserResponse {
    private NaturalPersonComplementResponse naturalPersonComplement;

    public GetAuthenticatedUserNaturalPersonResponse(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            AuthorizationRole authorizationRole,
            PersonType personType,
            NaturalPersonComplementResponse naturalPersonComplement
    ) {
        super(firstName, lastName, email, phoneNumber, authorizationRole, personType);
        this.naturalPersonComplement = naturalPersonComplement;
    }

    public static GetAuthenticatedUserNaturalPersonResponse fromOutput(GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput) {
        return new GetAuthenticatedUserNaturalPersonResponse(
                getAuthenticatedUserUseCaseOutput.getFirstName(),
                getAuthenticatedUserUseCaseOutput.getLastName(),
                getAuthenticatedUserUseCaseOutput.getEmail(),
                getAuthenticatedUserUseCaseOutput.getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.getPersonType(),
                NaturalPersonComplementResponse.fromOutput(getAuthenticatedUserUseCaseOutput.getPersonComplementOutput())
        );
    }
}
