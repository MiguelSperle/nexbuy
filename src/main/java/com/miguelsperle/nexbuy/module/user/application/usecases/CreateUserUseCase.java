package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.JuridicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.PhysicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IJuridicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IPhysicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserGateway userGateway;
    private final IPhysicalUserGateway physicalUserGateway;
    private final IJuridicalUserGateway juridicalUserGateway;
    private final IPasswordEncryptorProvider passwordEncryptor;

    @Override
    public CreateUserUseCaseOutput execute(CreateUserUseCaseInput createUserUseCaseInput) {
        this.verifyUserAlreadyExistsByEmail(createUserUseCaseInput.getEmail());

        if (Objects.equals(createUserUseCaseInput.getUserType(), UserType.PHYSICAL_USER.name())) {
            this.verifyPhysicalUserAlreadyExistsByCpf(createUserUseCaseInput.getCpf());
            this.verifyPhysicalUserAlreadyExistsByGeneralRegister(createUserUseCaseInput.getGeneralRegister());
        } else {
            this.verifyJuridicalUserAlreadyExistsByCnpj(createUserUseCaseInput.getCnpj());
            this.verifyJuridicalUserAlreadyExistsByFantasyName(createUserUseCaseInput.getFantasyName());
            this.verifyJuridicalUserAlreadyExistsByLegalName(createUserUseCaseInput.getLegalName());
            this.verifyJuridicalUserAlreadyExistsByStateRegistration(createUserUseCaseInput.getStateRegistration());
        }

        final String encryptedPassword = this.passwordEncryptor.encrypt(createUserUseCaseInput.getPassword());

        final UserType convertedUserType = UserType.valueOf(createUserUseCaseInput.getUserType());

        final User newUser = User.newUser(createUserUseCaseInput.getFirstName(), createUserUseCaseInput.getLastName(), createUserUseCaseInput.getEmail().toLowerCase(), encryptedPassword, createUserUseCaseInput.getPhoneNumber(), AuthorizationRole.CUSTOMER, convertedUserType);

        final User savedUser = this.userGateway.save(newUser);

        return new CreateUserUseCaseOutput(savedUser);
    }

    private void verifyUserAlreadyExistsByEmail(String email) {
        if (this.userGateway.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("This email is already being used");
        }
    }

    private void verifyPhysicalUserAlreadyExistsByCpf(String cpf) {
        if (this.physicalUserGateway.findByCpf(cpf).isPresent()) {
            throw new PhysicalUserAlreadyExistsException("This cpf is already being used");
        }
    }

    private void verifyPhysicalUserAlreadyExistsByGeneralRegister(String generalRegister) {
        if (this.physicalUserGateway.findByGeneralRegister(generalRegister).isPresent()) {
            throw new PhysicalUserAlreadyExistsException("This general register is already being used");
        }
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
}
