package com.miguelsperle.nexbuy.module.user.application.dtos.complements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuridicalUserInput {
    private String cnpj;
    private String fantasyName;
    private String legalName;
    private String stateRegistration;
}
