package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.LegalPersonInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.NaturalPersonInput;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ICreateLegalPersonUseCase createLegalPersonUseCase;
    private final ICreateNaturalPersonUseCase createNaturalPersonUseCase;
    private final ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase;
    private final ITransactionExecutor transactionExecutor;

    @Override
    public void execute(CreateUserUseCaseInput createUserUseCaseInput) {
        final PersonType convertedToPersonType = PersonType.valueOf(createUserUseCaseInput.getPersonType());

        this.ensureComplementBasedUserType(convertedToPersonType, createUserUseCaseInput.getNaturalPersonInput(), createUserUseCaseInput.getLegalPersonInput());

        if (this.verifyUserAlreadyExistsByEmail(createUserUseCaseInput.getEmail())) {
            throw new UserAlreadyExistsException("This email is already being used");
        }

        final String encryptedPassword = this.passwordEncryptorProvider.encode(createUserUseCaseInput.getPassword());

        final User newUser = User.newUser(createUserUseCaseInput.getFirstName(), createUserUseCaseInput.getLastName(), createUserUseCaseInput.getEmail().toLowerCase(), encryptedPassword, createUserUseCaseInput.getPhoneNumber(), convertedToPersonType);

        this.transactionExecutor.runTransaction(() -> {
            final User savedUser = this.userGateway.save(newUser);

            if (savedUser.getPersonType() == PersonType.NATURAL_PERSON) {
                this.createNaturalPersonUseCase.execute(new CreateNaturalPersonUseCaseInput(
                        savedUser,
                        createUserUseCaseInput.getNaturalPersonInput()
                ));
            } else {
                this.createLegalPersonUseCase.execute(new CreateLegalPersonUseCaseInput(
                        savedUser,
                        createUserUseCaseInput.getLegalPersonInput()
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

    private void ensureComplementBasedUserType(PersonType personType, NaturalPersonInput naturalPersonInput, LegalPersonInput legalPersonInput) {
        if (personType == PersonType.NATURAL_PERSON && naturalPersonInput == null) {
            throw new MissingRequiredComplementException("Natural person complement should not be null when user type is NATURAL_PERSON");
        } else if (personType == PersonType.LEGAL_PERSON && legalPersonInput == null) {
            throw new MissingRequiredComplementException("Legal person complement should not be null when user type is LEGAL_PERSON");
        }
    }
}
