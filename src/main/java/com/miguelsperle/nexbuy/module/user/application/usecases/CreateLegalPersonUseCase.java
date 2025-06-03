package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.LegalPersonAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.ILegalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateLegalPersonUseCase implements ICreateLegalPersonUseCase {
    private final ILegalPersonGateway legalPersonGateway;

    @Override
    public void execute(CreateLegalPersonUseCaseInput createLegalPersonUseCaseInput) {
        if (this.verifyLegalPersonAlreadyExistsByCnpj(createLegalPersonUseCaseInput.getLegalPersonDataInput().getCnpj())) {
            throw new LegalPersonAlreadyExistsException("This cnpj is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByFantasyName(createLegalPersonUseCaseInput.getLegalPersonDataInput().getFantasyName())) {
            throw new LegalPersonAlreadyExistsException("This fantasy name is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByLegalName(createLegalPersonUseCaseInput.getLegalPersonDataInput().getLegalName())) {
            throw new LegalPersonAlreadyExistsException("This legal name is already being used");
        }

        if (this.verifyLegalPersonAlreadyExistsByStateRegistration(createLegalPersonUseCaseInput.getLegalPersonDataInput().getStateRegistration())) {
            throw new LegalPersonAlreadyExistsException("This state registration is already being used");
        }

        final User user = createLegalPersonUseCaseInput.getUser();

        final LegalPerson newLegalPerson = LegalPerson.newLegalPerson(user, createLegalPersonUseCaseInput.getLegalPersonDataInput().getCnpj(), createLegalPersonUseCaseInput.getLegalPersonDataInput().getFantasyName(), createLegalPersonUseCaseInput.getLegalPersonDataInput().getLegalName(), createLegalPersonUseCaseInput.getLegalPersonDataInput().getStateRegistration());

        this.legalPersonGateway.save(newLegalPerson);
    }

    private boolean verifyLegalPersonAlreadyExistsByCnpj(String cnpj) {
        return this.legalPersonGateway.findByCnpj(cnpj).isPresent();
    }

    private boolean verifyLegalPersonAlreadyExistsByFantasyName(String fantasyName) {
        return this.legalPersonGateway.findByFantasyName(fantasyName).isPresent();
    }

    private boolean verifyLegalPersonAlreadyExistsByLegalName(String legalName) {
        return this.legalPersonGateway.findByLegalName(legalName).isPresent();
    }

    private boolean verifyLegalPersonAlreadyExistsByStateRegistration(String stateRegistration) {
        if (stateRegistration != null) {
            return this.legalPersonGateway.findByStateRegistration(stateRegistration).isPresent();
        }
        return false;
    }
}
