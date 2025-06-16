package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.bases.BaseAuthenticatedUserResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.LegalPersonComplementResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetAuthenticatedUserLegalPersonResponse extends BaseAuthenticatedUserResponse {
    private LegalPersonComplementResponse legalPersonComplement;

    public GetAuthenticatedUserLegalPersonResponse(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            AuthorizationRole authorizationRole,
            PersonType personType,
            LegalPersonComplementResponse legalPersonComplement
    ) {
        super(firstName, lastName, email, phoneNumber, authorizationRole, personType);
        this.legalPersonComplement = legalPersonComplement;
    }

    public static GetAuthenticatedUserLegalPersonResponse fromOutput(GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput) {
        return new GetAuthenticatedUserLegalPersonResponse(
                getAuthenticatedUserUseCaseOutput.getFirstName(),
                getAuthenticatedUserUseCaseOutput.getLastName(),
                getAuthenticatedUserUseCaseOutput.getEmail(),
                getAuthenticatedUserUseCaseOutput.getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.getPersonType(),
                LegalPersonComplementResponse.fromOutput(getAuthenticatedUserUseCaseOutput.getPersonComplementOutput())
        );
    }
}
