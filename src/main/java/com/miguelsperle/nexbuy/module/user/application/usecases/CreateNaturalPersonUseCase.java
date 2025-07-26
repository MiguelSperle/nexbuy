package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.NaturalPersonAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.INaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;

public class CreateNaturalPersonUseCase implements ICreateNaturalPersonUseCase {
    private final INaturalPersonRepository naturalPersonRepository;

    public CreateNaturalPersonUseCase(INaturalPersonRepository naturalPersonRepository) {
        this.naturalPersonRepository = naturalPersonRepository;
    }

    @Override
    public void execute(CreateNaturalPersonUseCaseInput createNaturalPersonUseCaseInput) {
        if (this.verifyNaturalPersonAlreadyExistsByCpf(createNaturalPersonUseCaseInput.cpf())) {
            throw NaturalPersonAlreadyExistsException.with("This cpf is already being used");
        }

        if (this.verifyNaturalPersonAlreadyExistsByGeneralRegister(createNaturalPersonUseCaseInput.generalRegister())) {
            throw NaturalPersonAlreadyExistsException.with("This general register is already being used");
        }

        final NaturalPerson newNaturalPerson = NaturalPerson.newNaturalPerson(
                createNaturalPersonUseCaseInput.userId(),
                createNaturalPersonUseCaseInput.cpf(),
                createNaturalPersonUseCaseInput.generalRegister()
        );

        this.saveNaturalPerson(newNaturalPerson);
    }

    private boolean verifyNaturalPersonAlreadyExistsByCpf(String cpf) {
        return this.naturalPersonRepository.existsByCpf(cpf);
    }

    private boolean verifyNaturalPersonAlreadyExistsByGeneralRegister(String generalRegister) {
        return this.naturalPersonRepository.existsByGeneralRegister(generalRegister);
    }

    private void saveNaturalPerson(NaturalPerson naturalPerson) {
        this.naturalPersonRepository.save(naturalPerson);
    }
}
