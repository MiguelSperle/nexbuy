package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.LegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.NaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.shared.domain.events.UserCreatedEvent;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptorProvider passwordEncryptorProvider;
    private final NaturalPersonRepository naturalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final TransactionExecutor transactionExecutor;
    private final MessageProducer messageProducer;

    private static final String USER_CREATED_EXCHANGE = "user.created.exchange";

    public CreateUserUseCaseImpl(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            NaturalPersonRepository naturalPersonRepository,
            LegalPersonRepository legalPersonRepository,
            TransactionExecutor transactionExecutor,
            MessageProducer messageProducer
    ) {
        this.userRepository = userRepository;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.naturalPersonRepository = naturalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
        this.transactionExecutor = transactionExecutor;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(CreateUserUseCaseInput createUserUseCaseInput) {
        this.ensureComplementBasedPersonType(createUserUseCaseInput.personType(), createUserUseCaseInput.personComplementInput());

        if (this.verifyUserAlreadyExistsByEmail(createUserUseCaseInput.email())) {
            throw DomainException.with("This email is already being used", 409);
        }

        if (createUserUseCaseInput.personType() == PersonType.NATURAL_PERSON) {
            this.validateNaturalPersonData(createUserUseCaseInput.personComplementInput());
        } else {
            this.validateLegalPersonData(createUserUseCaseInput.personComplementInput());
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(createUserUseCaseInput.password());

        final User newUser = User.newUser(createUserUseCaseInput.firstName(), createUserUseCaseInput.lastName(), createUserUseCaseInput.email().toLowerCase(), encodedPassword, createUserUseCaseInput.phoneNumber(), createUserUseCaseInput.personType());

        this.transactionExecutor.runTransaction(() -> {
            final User savedUser = this.saveUser(newUser);

            if (savedUser.getPersonType() == PersonType.NATURAL_PERSON) {
                final NaturalPerson newNaturalPerson = NaturalPerson.newNaturalPerson(
                        savedUser.getId(),
                        createUserUseCaseInput.personComplementInput().cpf(),
                        createUserUseCaseInput.personComplementInput().generalRegister()
                );

                this.saveNaturalPerson(newNaturalPerson);
            } else {
                final LegalPerson newLegalPerson = LegalPerson.newLegalPerson(
                        savedUser.getId(),
                        createUserUseCaseInput.personComplementInput().cnpj(),
                        createUserUseCaseInput.personComplementInput().fantasyName(),
                        createUserUseCaseInput.personComplementInput().legalName(),
                        createUserUseCaseInput.personComplementInput().stateRegistration()
                );

                this.saveLegalPerson(newLegalPerson);
            }

            final UserCreatedEvent userCreatedEvent = UserCreatedEvent.from(savedUser.getId());

            this.transactionExecutor.registerAfterCommit(() -> this.messageProducer.publish(
                    USER_CREATED_EXCHANGE, "", userCreatedEvent
            ));
        });
    }

    private void ensureComplementBasedPersonType(PersonType personType, PersonComplementInput personComplementInput) {
        if (personType == PersonType.NATURAL_PERSON && personComplementInput == null) {
            throw DomainException.with("You should provide naturalPerson when the person type is a NATURAL_PERSON", 400);
        } else if (personType == PersonType.LEGAL_PERSON && personComplementInput == null) {
            throw DomainException.with("You should provide legalPerson when the person type is a LEGAL_PERSON", 400);
        }
    }

    private boolean verifyUserAlreadyExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    private User saveUser(User user) {
        return this.userRepository.save(user);
    }

    private void validateNaturalPersonData(PersonComplementInput personComplementInput) {
        if (this.verifyNaturalPersonAlreadyExistsByCpf(personComplementInput.cpf())) {
            throw DomainException.with("This cpf is already being used", 409);
        }

        if (this.verifyNaturalPersonAlreadyExistsByGeneralRegister(personComplementInput.generalRegister())) {
            throw DomainException.with("This general register is already being used", 409);
        }
    }

    private boolean verifyNaturalPersonAlreadyExistsByCpf(String cpf) {
        return this.naturalPersonRepository.existsByCpf(cpf);
    }

    private boolean verifyNaturalPersonAlreadyExistsByGeneralRegister(String generalRegister) {
        return this.naturalPersonRepository.existsByGeneralRegister(generalRegister);
    }

    private void saveNaturalPerson(NaturalPerson naturalPerson) {
        this.naturalPersonRepository.save(naturalPerson);
    }

    private void validateLegalPersonData(PersonComplementInput personComplementInput) {
        if (this.verifyLegalPersonAlreadyExistsByCnpj(personComplementInput.cnpj())) {
            throw DomainException.with("This cnpj is already being used", 409);
        }

        if (this.verifyLegalPersonAlreadyExistsByFantasyName(personComplementInput.fantasyName())) {
            throw DomainException.with("This fantasy name is already being used", 409);
        }

        if (this.verifyLegalPersonAlreadyExistsByLegalName(personComplementInput.legalName())) {
            throw DomainException.with("This legal name is already being used", 409);
        }

        if (this.verifyLegalPersonAlreadyExistsByStateRegistration(personComplementInput.stateRegistration())) {
            throw DomainException.with("This state registration is already being used", 409);
        }
    }

    private boolean verifyLegalPersonAlreadyExistsByCnpj(String cnpj) {
        return this.legalPersonRepository.existsByCnpj(cnpj);
    }

    private boolean verifyLegalPersonAlreadyExistsByFantasyName(String fantasyName) {
        return this.legalPersonRepository.existsByFantasyName(fantasyName);
    }

    private boolean verifyLegalPersonAlreadyExistsByLegalName(String legalName) {
        return this.legalPersonRepository.existsByLegalName(legalName);
    }

    private boolean verifyLegalPersonAlreadyExistsByStateRegistration(String stateRegistration) {
        if (stateRegistration != null) {
            return this.legalPersonRepository.existsByStateRegistration(stateRegistration);
        }
        return false;
    }

    private void saveLegalPerson(LegalPerson legalPerson) {
        this.legalPersonRepository.save(legalPerson);
    }
}
