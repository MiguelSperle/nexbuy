package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.complements;

public record PersonComplementInput(
        String cpf,
        String generalRegister,
        String cnpj,
        String fantasyName,
        String legalName,
        String stateRegistration
) {
    public static PersonComplementInput with(
            String cpf,
            String generalRegister,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new PersonComplementInput(
                cpf,
                generalRegister,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration
        );
    }
}
