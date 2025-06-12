package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.NaturalPersonAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.INaturalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateNaturalPersonUseCase implements ICreateNaturalPersonUseCase {
    private final INaturalPersonGateway naturalPersonGateway;

    @Override
    public void execute(CreateNaturalPersonUseCaseInput createNaturalPersonUseCaseInput) {
        if (this.verifyNaturalPersonAlreadyExistsByCpf(createNaturalPersonUseCaseInput.getCpf())) {
            throw new NaturalPersonAlreadyExistsException("This cpf is already being used");
        }

        if (this.verifyNaturalPersonAlreadyExistsByGeneralRegister(createNaturalPersonUseCaseInput.getGeneralRegister())) {
            throw new NaturalPersonAlreadyExistsException("This general register is already being used");
        }

        final User user = createNaturalPersonUseCaseInput.getUser();

        final NaturalPerson newNaturalPerson = NaturalPerson.newNaturalPerson(user, createNaturalPersonUseCaseInput.getCpf(), createNaturalPersonUseCaseInput.getGeneralRegister());

        this.naturalPersonGateway.save(newNaturalPerson);
    }

    private boolean verifyNaturalPersonAlreadyExistsByCpf(String cpf) {
        return this.naturalPersonGateway.findByCpf(cpf).isPresent();
    }

    private boolean verifyNaturalPersonAlreadyExistsByGeneralRegister(String generalRegister) {
        return this.naturalPersonGateway.findByGeneralRegister(generalRegister).isPresent();
    }
}
