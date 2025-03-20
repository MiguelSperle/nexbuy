package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IPhysicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.PhysicalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePhysicalUserUseCase implements ICreatePhysicalUserUseCase {
    private final IPhysicalUserGateway physicalUserGateway;

    @Override
    public void execute(CreatePhysicalUserUseCaseInput createPhysicalUserUseCaseInput) {
        final PhysicalUser newUser = PhysicalUser.newPhysicalUser(createPhysicalUserUseCaseInput.getUser(), createPhysicalUserUseCaseInput.getCpf(), createPhysicalUserUseCaseInput.getGeneralRegister());

        this.physicalUserGateway.save(newUser);
    }
}
