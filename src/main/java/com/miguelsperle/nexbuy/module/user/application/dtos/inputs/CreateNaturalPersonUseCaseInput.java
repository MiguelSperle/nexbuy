package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record CreateNaturalPersonUseCaseInput(
        String userId,
        String cpf,
        String generalRegister
) {
    public static CreateNaturalPersonUseCaseInput with(
            String userId,
            String cpf,
            String generalRegister
    ) {
        return new CreateNaturalPersonUseCaseInput(
                userId,
                cpf,
                generalRegister
        );
    }
}
