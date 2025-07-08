package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public record CreateNaturalPersonUseCaseInput(
        User user,
        String cpf,
        String generalRegister
) {
}
