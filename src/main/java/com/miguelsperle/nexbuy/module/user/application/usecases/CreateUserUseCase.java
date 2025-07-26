package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingComplementException;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserRepository userRepository;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ICreateLegalPersonUseCase createLegalPersonUseCase;
    private final ICreateNaturalPersonUseCase createNaturalPersonUseCase;
    private final ICreateVerificationCodeUseCase createVerificationCodeUseCase;
    private final ITransactionExecutor transactionExecutor;

    public CreateUserUseCase(
            IUserRepository userRepository,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ICreateLegalPersonUseCase createLegalPersonUseCase,
            ICreateNaturalPersonUseCase createNaturalPersonUseCase,
            ICreateVerificationCodeUseCase createVerificationCodeUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        this.userRepository = userRepository;
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
        return this.userRepository.existsByEmail(email);
    }

    private void ensureComplementBasedPersonType(PersonType personType, PersonComplementInput personComplementInput) {
        if (personType == PersonType.NATURAL_PERSON && personComplementInput == null) {
            throw MissingComplementException.with("You should provide naturalPerson when the person type is a NATURAL_PERSON");
        } else if (personType == PersonType.LEGAL_PERSON && personComplementInput == null) {
            throw MissingComplementException.with("You should provide legalPerson when the person type is a LEGAL_PERSON");
        }
    }

    private User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
