package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.LegalPersonAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.ILegalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;

public class CreateLegalPersonUseCase implements ICreateLegalPersonUseCase {
    private final ILegalPersonGateway legalPersonGateway;

    public CreateLegalPersonUseCase(ILegalPersonGateway legalPersonGateway) {
        this.legalPersonGateway = legalPersonGateway;
    }

    @Override
    public void execute(CreateLegalPersonUseCaseInput createLegalPersonUseCaseInput) {
        if (this.verifyLegalPersonAlreadyExistsByCnpj(createLegalPersonUseCaseInput.cnpj())) {
            throw new LegalPersonAlreadyExistsException("This cnpj is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByFantasyName(createLegalPersonUseCaseInput.fantasyName())) {
            throw new LegalPersonAlreadyExistsException("This fantasy name is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByLegalName(createLegalPersonUseCaseInput.legalName())) {
            throw new LegalPersonAlreadyExistsException("This legal name is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByStateRegistration(createLegalPersonUseCaseInput.stateRegistration())) {
            throw new LegalPersonAlreadyExistsException("This state registration is already being used");
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
        return this.legalPersonGateway.existsByCnpj(cnpj);
    }

    private boolean verifyLegalPersonAlreadyExistsByFantasyName(String fantasyName) {
        return this.legalPersonGateway.existsByFantasyName(fantasyName);
    }

    private boolean verifyLegalPersonAlreadyExistsByLegalName(String legalName) {
        return this.legalPersonGateway.existsByLegalName(legalName);
    }

    private boolean verifyLegalPersonAlreadyExistsByStateRegistration(String stateRegistration) {
        if (stateRegistration != null) {
            return this.legalPersonGateway.existsByStateRegistration(stateRegistration);
        }
        return false;
    }

    private void saveLegalPerson(LegalPerson legalPerson) {
        this.legalPersonGateway.save(legalPerson);
    }
}
