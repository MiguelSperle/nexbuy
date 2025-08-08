package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.NaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class CreateNaturalPersonUseCaseImpl implements CreateNaturalPersonUseCase {
    private final NaturalPersonRepository naturalPersonRepository;

    public CreateNaturalPersonUseCaseImpl(NaturalPersonRepository naturalPersonRepository) {
        this.naturalPersonRepository = naturalPersonRepository;
    }

    @Override
    public void execute(CreateNaturalPersonUseCaseInput createNaturalPersonUseCaseInput) {
        if (this.verifyNaturalPersonAlreadyExistsByCpf(createNaturalPersonUseCaseInput.cpf())) {
            throw DomainException.with("This cpf is already being used", 409);
        }

        if (this.verifyNaturalPersonAlreadyExistsByGeneralRegister(createNaturalPersonUseCaseInput.generalRegister())) {
            throw DomainException.with("This general register is already being used", 409);
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
