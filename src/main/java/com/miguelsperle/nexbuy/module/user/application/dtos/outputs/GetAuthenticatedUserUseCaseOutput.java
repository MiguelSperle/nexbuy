package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.LegalPersonDataOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.NaturalPersonDataOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAuthenticatedUserUseCaseOutput {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AuthorizationRole authorizationRole;
    private PersonType personType;
    private NaturalPersonDataOutput naturalPersonDataOutput;
    private LegalPersonDataOutput legalPersonDataOutput;
}
