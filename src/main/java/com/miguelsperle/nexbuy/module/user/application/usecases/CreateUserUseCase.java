package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ICreateJuridicalUserUseCase createJuridicalUserUseCase;
    private final ICreatePhysicalUserUseCase createPhysicalUserUseCase;
    private final ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase;

    @Override
    @Transactional
    public void execute(CreateUserUseCaseInput createUserUseCaseInput) {
        this.verifyUserAlreadyExistsByEmail(createUserUseCaseInput.getEmail());

        final String encryptedPassword = this.passwordEncryptorProvider.encrypt(createUserUseCaseInput.getPassword());

        final UserType convertedToUserType = UserType.valueOf(createUserUseCaseInput.getUserType());

        final User newUser = User.newUser(createUserUseCaseInput.getFirstName(), createUserUseCaseInput.getLastName(), createUserUseCaseInput.getEmail().toLowerCase(), encryptedPassword, createUserUseCaseInput.getPhoneNumber(), AuthorizationRole.CUSTOMER, convertedToUserType);

        final User savedUser = this.userGateway.save(newUser);

        if (Objects.equals(savedUser.getUserType(), UserType.PHYSICAL_USER)) {
            this.createPhysicalUserUseCase.execute(new CreatePhysicalUserUseCaseInput(
                    savedUser.getId(),
                    createUserUseCaseInput.getPhysicalUserInputComplement().getCpf(),
                    createUserUseCaseInput.getPhysicalUserInputComplement().getGeneralRegister()
            ));
        } else {
            this.createJuridicalUserUseCase.execute(new CreateJuridicalUserUseCaseInput(
                    savedUser.getId(),
                    createUserUseCaseInput.getJuridicalUserInputComplement().getCnpj(),
                    createUserUseCaseInput.getJuridicalUserInputComplement().getFantasyName(),
                    createUserUseCaseInput.getJuridicalUserInputComplement().getLegalName(),
                    createUserUseCaseInput.getJuridicalUserInputComplement().getStateRegistration()
            ));
        }

        this.createUserVerificationCodeUseCase.execute(new CreateUserVerificationCodeUseCaseInput(
                savedUser.getId()
        ));
    }

    private void verifyUserAlreadyExistsByEmail(String email) {
        if (this.userGateway.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("This email is already being used");
        }
    }
}
