package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ICreateLegalPersonUseCase createLegalPersonUseCase;
    private final ICreateNaturalPersonUseCase createNaturalPersonUseCase;
    private final ICreateVerificationCodeUseCase createVerificationCodeUseCase;
    private final ITransactionExecutor transactionExecutor;

    public CreateUserUseCase(
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ICreateLegalPersonUseCase createLegalPersonUseCase,
            ICreateNaturalPersonUseCase createNaturalPersonUseCase,
            ICreateVerificationCodeUseCase createVerificationCodeUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        this.userGateway = userGateway;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.createLegalPersonUseCase = createLegalPersonUseCase;
        this.createNaturalPersonUseCase = createNaturalPersonUseCase;
        this.createVerificationCodeUseCase = createVerificationCodeUseCase;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(CreateUserUseCaseInput createUserUseCaseInput) {
        this.ensureComplementBasedPersonType(createUserUseCaseInput.personType(), createUserUseCaseInput.personComplementInput());

        if (this.verifyUserAlreadyExistsByEmail(createUserUseCaseInput.email())) {
            throw UserAlreadyExistsException.with("This email is already being used");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(createUserUseCaseInput.password());

        final User newUser = User.newUser(createUserUseCaseInput.firstName(), createUserUseCaseInput.lastName(), createUserUseCaseInput.email().toLowerCase(), encodedPassword, createUserUseCaseInput.phoneNumber(), createUserUseCaseInput.personType());

        this.transactionExecutor.runTransaction(() -> {
            final User savedUser = this.saveUser(newUser);

            if (savedUser.getPersonType() == PersonType.NATURAL_PERSON) {
                this.createNaturalPersonUseCase.execute(CreateNaturalPersonUseCaseInput.with(
                        savedUser.getId(),
                        createUserUseCaseInput.personComplementInput().cpf(),
                        createUserUseCaseInput.personComplementInput().generalRegister()
                ));
            } else {
                this.createLegalPersonUseCase.execute(CreateLegalPersonUseCaseInput.with(
                        savedUser.getId(),
                        createUserUseCaseInput.personComplementInput().cnpj(),
                        createUserUseCaseInput.personComplementInput().fantasyName(),
                        createUserUseCaseInput.personComplementInput().legalName(),
                        createUserUseCaseInput.personComplementInput().stateRegistration()
                ));
            }

            this.createVerificationCodeUseCase.execute(CreateVerificationCodeUseCaseInput.with(
                    savedUser.getId()
            ));
        });
    }

    private boolean verifyUserAlreadyExistsByEmail(String email) {
        return this.userGateway.existsByEmail(email);
    }

    private void ensureComplementBasedPersonType(PersonType personType, PersonComplementInput personComplementInput) {
        if (personType == PersonType.NATURAL_PERSON && personComplementInput == null) {
            throw MissingRequiredComplementException.with("You should provide naturalPerson when the person type is a NATURAL_PERSON");
        } else if (personType == PersonType.LEGAL_PERSON && personComplementInput == null) {
            throw MissingRequiredComplementException.with("You should provide legalPerson when the person type is a LEGAL_PERSON");
        }
    }

    private User saveUser(User user) {
        return this.userGateway.save(user);
    }
}
