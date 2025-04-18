package com.miguelsperle.nexbuy.module.user.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateJuridicalUserUseCaseInput {
    private String userId;
    private String cnpj;
    private String fantasyName;
    private String legalName;
    private String stateRegistration;
}
