package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.NaturalPersonDataInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNaturalPersonUseCaseInput {
    private User user;
    private NaturalPersonDataInput naturalPersonDataInput;
}
