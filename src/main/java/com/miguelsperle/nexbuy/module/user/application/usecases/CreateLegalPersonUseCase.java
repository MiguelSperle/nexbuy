package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreateLegalPersonUseCaseInput;
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
        if (this.verifyJuridicalUserAlreadyExistsByCnpj(createLegalPersonUseCaseInput.getLegalPersonInput().getCnpj())) {
            throw new LegalPersonAlreadyExistsException("This cnpj is already being used");
        }

        if (this.verifyJuridicalUserAlreadyExistsByFantasyName(createLegalPersonUseCaseInput.getLegalPersonInput().getFantasyName())) {
            throw new LegalPersonAlreadyExistsException("This fantasy name is already being used");
        }

        if (this.verifyJuridicalUserAlreadyExistsByLegalName(createLegalPersonUseCaseInput.getLegalPersonInput().getLegalName())) {
            throw new LegalPersonAlreadyExistsException("This legal name is already being used");
        }

        if (this.verifyJuridicalUserAlreadyExistsByStateRegistration(createLegalPersonUseCaseInput.getLegalPersonInput().getStateRegistration())) {
            throw new LegalPersonAlreadyExistsException("This state registration is already being used");
        }

        final User user = createLegalPersonUseCaseInput.getUser();

        final LegalPerson newLegalPerson = LegalPerson.newLegalPerson(user, createLegalPersonUseCaseInput.getLegalPersonInput().getCnpj(), createLegalPersonUseCaseInput.getLegalPersonInput().getFantasyName(), createLegalPersonUseCaseInput.getLegalPersonInput().getLegalName(), createLegalPersonUseCaseInput.getLegalPersonInput().getStateRegistration());

        this.legalPersonGateway.save(newLegalPerson);
    }

    private boolean verifyJuridicalUserAlreadyExistsByCnpj(String cnpj) {
        return this.legalPersonGateway.findByCnpj(cnpj).isPresent();
    }

    private boolean verifyJuridicalUserAlreadyExistsByFantasyName(String fantasyName) {
        return this.legalPersonGateway.findByFantasyName(fantasyName).isPresent();
    }

    private boolean verifyJuridicalUserAlreadyExistsByLegalName(String legalName) {
        return this.legalPersonGateway.findByLegalName(legalName).isPresent();
    }

    private boolean verifyJuridicalUserAlreadyExistsByStateRegistration(String stateRegistration) {
        if (stateRegistration != null) {
            return this.legalPersonGateway.findByStateRegistration(stateRegistration).isPresent();
        }
        return false;
    }
}
