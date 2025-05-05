package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.PhysicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IPhysicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.PhysicalUser;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePhysicalUserUseCase implements ICreatePhysicalUserUseCase {
    private final IPhysicalUserGateway physicalUserGateway;

    @Override
    public void execute(CreatePhysicalUserUseCaseInput createPhysicalUserUseCaseInput) {
        if (this.verifyPhysicalUserAlreadyExistsByCpf(createPhysicalUserUseCaseInput.getCpf())) {
            throw new PhysicalUserAlreadyExistsException("This cpf is already being used");
        }

        if (this.verifyPhysicalUserAlreadyExistsByGeneralRegister(createPhysicalUserUseCaseInput.getGeneralRegister())) {
            throw new PhysicalUserAlreadyExistsException("This general register is already being used");
        }

        final User user = createPhysicalUserUseCaseInput.getUser();

        final PhysicalUser newUser = PhysicalUser.newPhysicalUser(user, createPhysicalUserUseCaseInput.getCpf(), createPhysicalUserUseCaseInput.getGeneralRegister());

        this.physicalUserGateway.save(newUser);
    }

    private boolean verifyPhysicalUserAlreadyExistsByCpf(String cpf) {
        return this.physicalUserGateway.findByCpf(cpf).isPresent();
    }

    private boolean verifyPhysicalUserAlreadyExistsByGeneralRegister(String generalRegister) {
        return this.physicalUserGateway.findByGeneralRegister(generalRegister).isPresent();
    }
}
