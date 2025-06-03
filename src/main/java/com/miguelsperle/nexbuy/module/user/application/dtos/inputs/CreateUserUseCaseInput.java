package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.LegalPersonDataInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.NaturalPersonDataInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserUseCaseInput {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String personType;
    private NaturalPersonDataInput naturalPersonDataInput;
    private LegalPersonDataInput legalPersonDataInput;
}
