package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public record CreateNaturalPersonUseCaseInput(
        User user,
        String cpf,
        String generalRegister
) {
    public static CreateNaturalPersonUseCaseInput with(
            User user,
            String cpf,
            String generalRegister
    ) {
        return new CreateNaturalPersonUseCaseInput(
                user,
                cpf,
                generalRegister
        );
    }
}
