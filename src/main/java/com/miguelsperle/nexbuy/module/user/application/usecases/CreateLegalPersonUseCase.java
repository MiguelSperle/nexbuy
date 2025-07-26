package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.LegalPersonAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.ILegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;

public class CreateLegalPersonUseCase implements ICreateLegalPersonUseCase {
    private final ILegalPersonRepository legalPersonRepository;

    public CreateLegalPersonUseCase(ILegalPersonRepository legalPersonRepository) {
        this.legalPersonRepository = legalPersonRepository;
    }

    @Override
    public void execute(CreateLegalPersonUseCaseInput createLegalPersonUseCaseInput) {
        if (this.verifyLegalPersonAlreadyExistsByCnpj(createLegalPersonUseCaseInput.cnpj())) {
            throw LegalPersonAlreadyExistsException.with("This cnpj is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByFantasyName(createLegalPersonUseCaseInput.fantasyName())) {
            throw LegalPersonAlreadyExistsException.with("This fantasy name is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByLegalName(createLegalPersonUseCaseInput.legalName())) {
            throw LegalPersonAlreadyExistsException.with("This legal name is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByStateRegistration(createLegalPersonUseCaseInput.stateRegistration())) {
            throw LegalPersonAlreadyExistsException.with("This state registration is already being used");
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
