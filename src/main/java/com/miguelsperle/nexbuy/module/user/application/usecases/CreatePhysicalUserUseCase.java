package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.PhysicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IPhysicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.PhysicalUser;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePhysicalUserUseCase implements ICreatePhysicalUserUseCase {
    private final IPhysicalUserGateway physicalUserGateway;
    private final IUserGateway userGateway;

    @Override
    public void execute(CreatePhysicalUserUseCaseInput createPhysicalUserUseCaseInput) {
        this.verifyPhysicalUserAlreadyExistsByCpf(createPhysicalUserUseCaseInput.getCpf());

        this.verifyPhysicalUserAlreadyExistsByGeneralRegister(createPhysicalUserUseCaseInput.getGeneralRegister());

        final User user = this.getUserById(createPhysicalUserUseCaseInput.getUserId());

        final PhysicalUser newUser = PhysicalUser.newPhysicalUser(user, createPhysicalUserUseCaseInput.getCpf(), createPhysicalUserUseCaseInput.getGeneralRegister());

        this.physicalUserGateway.save(newUser);
    }

    private void verifyPhysicalUserAlreadyExistsByCpf(String cpf) {
        if (this.physicalUserGateway.findByCpf(cpf).isPresent()) {
            throw new PhysicalUserAlreadyExistsException("This cpf is already being used");
        }
    }

    private void verifyPhysicalUserAlreadyExistsByGeneralRegister(String generalRegister) {
        if (this.physicalUserGateway.findByGeneralRegister(generalRegister).isPresent()) {
            throw new PhysicalUserAlreadyExistsException("This general register is already being used");
        }
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
