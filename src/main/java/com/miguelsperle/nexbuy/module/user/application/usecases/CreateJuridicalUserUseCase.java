package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.JuridicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IJuridicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalUser;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateJuridicalUserUseCase implements ICreateJuridicalUserUseCase {
    private final IJuridicalUserGateway juridicalUserGateway;

    @Override
    public void execute(CreateJuridicalUserUseCaseInput createJuridicalUserUseCaseInput) {
        if (this.verifyJuridicalUserAlreadyExistsByCnpj(createJuridicalUserUseCaseInput.getCnpj())) {
            throw new JuridicalUserAlreadyExistsException("This cnpj is already being used");
        }

        if (this.verifyJuridicalUserAlreadyExistsByFantasyName(createJuridicalUserUseCaseInput.getFantasyName())) {
            throw new JuridicalUserAlreadyExistsException("This fantasy name is already being used");
        }

        if (this.verifyJuridicalUserAlreadyExistsByLegalName(createJuridicalUserUseCaseInput.getLegalName())) {
            throw new JuridicalUserAlreadyExistsException("This legal name is already being used");
        }

        if (this.verifyJuridicalUserAlreadyExistsByStateRegistration(createJuridicalUserUseCaseInput.getStateRegistration())) {
            throw new JuridicalUserAlreadyExistsException("This state registration is already being used");
        }

        final User user = createJuridicalUserUseCaseInput.getUser();

        final JuridicalUser newJuridicalUser = JuridicalUser.newJuridicalUser(user, createJuridicalUserUseCaseInput.getCnpj(), createJuridicalUserUseCaseInput.getFantasyName(), createJuridicalUserUseCaseInput.getLegalName(), createJuridicalUserUseCaseInput.getStateRegistration());

        this.juridicalUserGateway.save(newJuridicalUser);
    }

    private boolean verifyJuridicalUserAlreadyExistsByCnpj(String cnpj) {
        return this.juridicalUserGateway.findByCnpj(cnpj).isPresent();
    }

    private boolean verifyJuridicalUserAlreadyExistsByFantasyName(String fantasyName) {
        return this.juridicalUserGateway.findByFantasyName(fantasyName).isPresent();
    }

    private boolean verifyJuridicalUserAlreadyExistsByLegalName(String legalName) {
        return this.juridicalUserGateway.findByLegalName(legalName).isPresent();
    }

    private boolean verifyJuridicalUserAlreadyExistsByStateRegistration(String stateRegistration) {
        if (stateRegistration != null) {
            return this.juridicalUserGateway.findByStateRegistration(stateRegistration).isPresent();
        }
        return false;
    }
}
