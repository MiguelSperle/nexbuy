package com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaturalPersonDataInput {
    private String cpf;
    private String generalRegister;
}
