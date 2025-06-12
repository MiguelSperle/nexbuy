package com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaturalPersonComplementOutput {
    private String cpf;
    private String generalRegister;
}
