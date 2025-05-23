package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreateNaturalPersonUseCaseInput;
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
        if (this.verifyNaturalPersonAlreadyExistsByCpf(createNaturalPersonUseCaseInput.getNaturalPersonInput().getCpf())) {
            throw new NaturalPersonAlreadyExistsException("This cpf is already being used");
        }

        if (this.verifyPNaturalPersonAlreadyExistsByGeneralRegister(createNaturalPersonUseCaseInput.getNaturalPersonInput().getGeneralRegister())) {
            throw new NaturalPersonAlreadyExistsException("This general register is already being used");
        }

        final User user = createNaturalPersonUseCaseInput.getUser();

        final NaturalPerson newNaturalPerson = NaturalPerson.newNaturalPerson(user, createNaturalPersonUseCaseInput.getNaturalPersonInput().getCpf(), createNaturalPersonUseCaseInput.getNaturalPersonInput().getGeneralRegister());

        this.naturalPersonGateway.save(newNaturalPerson);
    }

    private boolean verifyNaturalPersonAlreadyExistsByCpf(String cpf) {
        return this.naturalPersonGateway.findByCpf(cpf).isPresent();
    }

    private boolean verifyPNaturalPersonAlreadyExistsByGeneralRegister(String generalRegister) {
        return this.naturalPersonGateway.findByGeneralRegister(generalRegister).isPresent();
    }
}
