package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IJuridicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateJuridicalUserUseCase implements ICreateJuridicalUserUseCase {
    private final IJuridicalUserGateway juridicalUserGateway;

    @Override
    public void execute(CreateJuridicalUserUseCaseInput createJuridicalUserUseCaseInput) {
        final JuridicalUser newJuridicalUser = JuridicalUser.newJuridicalUser(createJuridicalUserUseCaseInput.getUser(), createJuridicalUserUseCaseInput.getCnpj(), createJuridicalUserUseCaseInput.getFantasyName(), createJuridicalUserUseCaseInput.getLegalName(), createJuridicalUserUseCaseInput.getStateRegistration());

        this.juridicalUserGateway.save(newJuridicalUser);
    }
}
