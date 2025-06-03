package com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalPersonDataOutput {
    private String cnpj;
    private String fantasyName;
    private String legalName;
    private String stateRegistration;
}
