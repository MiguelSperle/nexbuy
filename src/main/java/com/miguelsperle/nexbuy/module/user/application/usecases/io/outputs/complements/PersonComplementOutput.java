package com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.complements;

public record PersonComplementOutput(
        String cpf,
        String generalRegister,
        String cnpj,
        String fantasyName,
        String legalName,
        String stateRegistration
) {
    public static PersonComplementOutput from(
            String cpf,
            String generalRegister,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new PersonComplementOutput(
                cpf,
                generalRegister,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration
        );
    }
}

