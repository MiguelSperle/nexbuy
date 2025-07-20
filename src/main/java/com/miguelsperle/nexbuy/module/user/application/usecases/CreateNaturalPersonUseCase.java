package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.NaturalPersonAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.INaturalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;

public class CreateNaturalPersonUseCase implements ICreateNaturalPersonUseCase {
    private final INaturalPersonGateway naturalPersonGateway;

    public CreateNaturalPersonUseCase(INaturalPersonGateway naturalPersonGateway) {
        this.naturalPersonGateway = naturalPersonGateway;
    }

    @Override
    public void execute(CreateNaturalPersonUseCaseInput createNaturalPersonUseCaseInput) {
        if (this.verifyNaturalPersonAlreadyExistsByCpf(createNaturalPersonUseCaseInput.cpf())) {
            throw new NaturalPersonAlreadyExistsException("This cpf is already being used");
        }

        if (this.verifyNaturalPersonAlreadyExistsByGeneralRegister(createNaturalPersonUseCaseInput.generalRegister())) {
            throw new NaturalPersonAlreadyExistsException("This general register is already being used");
        }

        final NaturalPerson newNaturalPerson = NaturalPerson.newNaturalPerson(
                createNaturalPersonUseCaseInput.userId(),
                createNaturalPersonUseCaseInput.cpf(),
                createNaturalPersonUseCaseInput.generalRegister()
        );

        this.saveNaturalPerson(newNaturalPerson);
    }

    private boolean verifyNaturalPersonAlreadyExistsByCpf(String cpf) {
        return this.naturalPersonGateway.existsByCpf(cpf);
    }

    private boolean verifyNaturalPersonAlreadyExistsByGeneralRegister(String generalRegister) {
        return this.naturalPersonGateway.existsByGeneralRegister(generalRegister);
    }

    private void saveNaturalPerson(NaturalPerson naturalPerson) {
        this.naturalPersonGateway.save(naturalPerson);
    }
}
