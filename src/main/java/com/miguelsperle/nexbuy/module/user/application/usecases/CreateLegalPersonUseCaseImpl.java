package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.LegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class CreateLegalPersonUseCaseImpl implements CreateLegalPersonUseCase {
    private final LegalPersonRepository legalPersonRepository;

    public CreateLegalPersonUseCaseImpl(LegalPersonRepository legalPersonRepository) {
        this.legalPersonRepository = legalPersonRepository;
    }

    @Override
    public void execute(CreateLegalPersonUseCaseInput createLegalPersonUseCaseInput) {
        if (this.verifyLegalPersonAlreadyExistsByCnpj(createLegalPersonUseCaseInput.cnpj())) {
            throw DomainException.with("This cnpj is already being used", 409);
        }

        if (this.verifyLegalPersonAlreadyExistsByFantasyName(createLegalPersonUseCaseInput.fantasyName())) {
            throw DomainException.with("This fantasy name is already being used", 409);
        }

        if (this.verifyLegalPersonAlreadyExistsByLegalName(createLegalPersonUseCaseInput.legalName())) {
            throw DomainException.with("This legal name is already being used", 409);
        }

        if (this.verifyLegalPersonAlreadyExistsByStateRegistration(createLegalPersonUseCaseInput.stateRegistration())) {
            throw DomainException.with("This state registration is already being used", 409);
        }

        final LegalPerson newLegalPerson = LegalPerson.newLegalPerson(
                createLegalPersonUseCaseInput.userId(),
                createLegalPersonUseCaseInput.cnpj(),
                createLegalPersonUseCaseInput.fantasyName(),
                createLegalPersonUseCaseInput.legalName(),
                createLegalPersonUseCaseInput.stateRegistration()
        );

        this.saveLegalPerson(newLegalPerson);
    }

    private boolean verifyLegalPersonAlreadyExistsByCnpj(String cnpj) {
        return this.legalPersonRepository.existsByCnpj(cnpj);
    }

    private boolean verifyLegalPersonAlreadyExistsByFantasyName(String fantasyName) {
        return this.legalPersonRepository.existsByFantasyName(fantasyName);
    }

    private boolean verifyLegalPersonAlreadyExistsByLegalName(String legalName) {
        return this.legalPersonRepository.existsByLegalName(legalName);
    }

    private boolean verifyLegalPersonAlreadyExistsByStateRegistration(String stateRegistration) {
        if (stateRegistration != null) {
            return this.legalPersonRepository.existsByStateRegistration(stateRegistration);
        }
        return false;
    }

    private void saveLegalPerson(LegalPerson legalPerson) {
        this.legalPersonRepository.save(legalPerson);
    }
}
