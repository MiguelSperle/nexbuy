package com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements;

public record PersonComplementInput(
        String cpf,
        String generalRegister,
        String cnpj,
        String fantasyName,
        String legalName,
        String stateRegistration
) {
}
