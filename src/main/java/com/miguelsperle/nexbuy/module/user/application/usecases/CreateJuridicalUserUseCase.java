package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.JuridicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IJuridicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalUser;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateJuridicalUserUseCase implements ICreateJuridicalUserUseCase {
    private final IJuridicalUserGateway juridicalUserGateway;
    private final IUserGateway userGateway;

    @Override
    public void execute(CreateJuridicalUserUseCaseInput createJuridicalUserUseCaseInput) {
        this.verifyJuridicalUserAlreadyExistsByCnpj(createJuridicalUserUseCaseInput.getCnpj());

        this.verifyJuridicalUserAlreadyExistsByFantasyName(createJuridicalUserUseCaseInput.getFantasyName());

        this.verifyJuridicalUserAlreadyExistsByLegalName(createJuridicalUserUseCaseInput.getLegalName());

        this.verifyJuridicalUserAlreadyExistsByStateRegistration(createJuridicalUserUseCaseInput.getStateRegistration());

        final User user = this.getUserById(createJuridicalUserUseCaseInput.getUserId());

        final JuridicalUser newJuridicalUser = JuridicalUser.newJuridicalUser(user, createJuridicalUserUseCaseInput.getCnpj(), createJuridicalUserUseCaseInput.getFantasyName(), createJuridicalUserUseCaseInput.getLegalName(), createJuridicalUserUseCaseInput.getStateRegistration());

        this.juridicalUserGateway.save(newJuridicalUser);
    }

    private void verifyJuridicalUserAlreadyExistsByCnpj(String cnpj) {
        if (this.juridicalUserGateway.findByCnpj(cnpj).isPresent()) {
            throw new JuridicalUserAlreadyExistsException("This cnpj is already being used");
        }
    }

    private void verifyJuridicalUserAlreadyExistsByFantasyName(String fantasyName) {
        if (this.juridicalUserGateway.findByFantasyName(fantasyName).isPresent()) {
            throw new JuridicalUserAlreadyExistsException("This fantasy name is already being used");
        }
    }

    private void verifyJuridicalUserAlreadyExistsByLegalName(String legalName) {
        if (this.juridicalUserGateway.findByLegalName(legalName).isPresent()) {
            throw new JuridicalUserAlreadyExistsException("This legal name is already being used");
        }
    }

    private void verifyJuridicalUserAlreadyExistsByStateRegistration(String stateRegistration) {
        if (stateRegistration != null) {
            if (this.juridicalUserGateway.findByStateRegistration(stateRegistration).isPresent()) {
                throw new JuridicalUserAlreadyExistsException("This state registration is already being used");
            }
        }
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
