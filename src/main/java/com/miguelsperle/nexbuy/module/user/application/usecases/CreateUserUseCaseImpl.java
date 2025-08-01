package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingComplementException;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCreatedEvent;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptorProvider passwordEncryptorProvider;
    private final CreateLegalPersonUseCase createLegalPersonUseCase;
    private final CreateNaturalPersonUseCase createNaturalPersonUseCase;
    private final TransactionExecutor transactionExecutor;
    private final DomainEventPublisherProvider domainEventPublisherProvider;

    public CreateUserUseCaseImpl(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            CreateLegalPersonUseCase createLegalPersonUseCase,
            CreateNaturalPersonUseCase createNaturalPersonUseCase,
            TransactionExecutor transactionExecutor,
            DomainEventPublisherProvider domainEventPublisherProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.createLegalPersonUseCase = createLegalPersonUseCase;
        this.createNaturalPersonUseCase = createNaturalPersonUseCase;
        this.transactionExecutor = transactionExecutor;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
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

            this.transactionExecutor.registerAfterCommit(() -> this.domainEventPublisherProvider.publishEvent(UserCreatedEvent.from(
                    savedUser.getId()
            )));
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
