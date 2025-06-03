package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.LegalPersonDataInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLegalPersonUseCaseInput {
    private User user;
    private LegalPersonDataInput legalPersonDataInput;
}
