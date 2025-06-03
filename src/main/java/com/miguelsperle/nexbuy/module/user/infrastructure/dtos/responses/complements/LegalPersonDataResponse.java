package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalPersonDataResponse {
    private String cnpj;
    private String fantasyName;
    private String legalName;
    private String stateRegistration;
}
