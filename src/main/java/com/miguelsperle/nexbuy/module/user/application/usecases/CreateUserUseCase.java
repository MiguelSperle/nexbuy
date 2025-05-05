package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.JuridicalUserInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.PhysicalUserInput;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ICreateJuridicalUserUseCase createJuridicalUserUseCase;
    private final ICreatePhysicalUserUseCase createPhysicalUserUseCase;
    private final ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase;
    private final ITransactionExecutor transactionExecutor;

    @Override
    public void execute(CreateUserUseCaseInput createUserUseCaseInput) {
        transactionExecutor.runInTransaction(() -> {
            final UserType convertedToUserType = UserType.valueOf(createUserUseCaseInput.getUserType());

            this.ensureComplementBasedUserType(convertedToUserType, createUserUseCaseInput.getPhysicalUserInput(), createUserUseCaseInput.getJuridicalUserInput());

            if (this.verifyUserAlreadyExistsByEmail(createUserUseCaseInput.getEmail())) {
                throw new UserAlreadyExistsException("This email is already being used");
            }

            final String encryptedPassword = this.passwordEncryptorProvider.encrypt(createUserUseCaseInput.getPassword());

            final User newUser = User.newUser(createUserUseCaseInput.getFirstName(), createUserUseCaseInput.getLastName(), createUserUseCaseInput.getEmail().toLowerCase(), encryptedPassword, createUserUseCaseInput.getPhoneNumber(), AuthorizationRole.CUSTOMER, convertedToUserType);

            final User savedUser = this.userGateway.save(newUser);

            if (savedUser.getUserType() == UserType.PHYSICAL_USER) {
                this.createPhysicalUserUseCase.execute(new CreatePhysicalUserUseCaseInput(
                        savedUser,
                        createUserUseCaseInput.getPhysicalUserInput().getCpf(),
                        createUserUseCaseInput.getPhysicalUserInput().getGeneralRegister()
                ));
            } else {
                this.createJuridicalUserUseCase.execute(new CreateJuridicalUserUseCaseInput(
                        savedUser,
                        createUserUseCaseInput.getJuridicalUserInput().getCnpj(),
                        createUserUseCaseInput.getJuridicalUserInput().getFantasyName(),
                        createUserUseCaseInput.getJuridicalUserInput().getLegalName(),
                        createUserUseCaseInput.getJuridicalUserInput().getStateRegistration()
                ));
            }

            this.createUserVerificationCodeUseCase.execute(new CreateUserVerificationCodeUseCaseInput(
                    savedUser
            ));
        });
    }

    private boolean verifyUserAlreadyExistsByEmail(String email) {
        return this.userGateway.findByEmail(email).isPresent();
    }

    private void ensureComplementBasedUserType(UserType userType, PhysicalUserInput physicalUserInput, JuridicalUserInput juridicalUserInput) {
        if (userType == UserType.PHYSICAL_USER && physicalUserInput == null) {
            throw new MissingRequiredComplementException("Physical user complement should not be null when user type is PHYSICAL_USER");
        } else if (userType == UserType.JURIDICAL_USER && juridicalUserInput == null) {
            throw new MissingRequiredComplementException("Juridical user complement should not be null when user type is JURIDICAL_USER");
        }
    }
}
