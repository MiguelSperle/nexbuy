package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.LegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.NaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.LegalPersonBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.NaturalPersonBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptorProvider passwordEncryptorProvider;

    @Mock
    private NaturalPersonRepository naturalPersonRepository;

    @Mock
    private LegalPersonRepository legalPersonRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Mock
    private MessageProducer messageProducer;


    @Test
    @DisplayName("Should create user with natural person data")
    public void should_create_user_with_natural_person_data() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final NaturalPerson naturalPerson = NaturalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.naturalPersonRepository.existsByCpf(Mockito.any())).thenReturn(false);

        Mockito.when(this.naturalPersonRepository.existsByGeneralRegister(Mockito.any())).thenReturn(false);

        Mockito.when(this.passwordEncryptorProvider.encode(Mockito.any())).thenReturn(encodedPassword);

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(this.naturalPersonRepository.save(Mockito.any())).thenReturn(naturalPerson);

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).registerAfterCommit(Mockito.any());

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                naturalPerson.getCpf(), naturalPerson.getGeneralRegister(),
                null, null, null, null
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        this.createUserUseCase.execute(createUserUseCaseInput);

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).existsByCpf(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).existsByGeneralRegister(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).registerAfterCommit(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should create user with legal person data")
    public void should_create_user_with_legal_person_data() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );
        final LegalPerson legalPerson = LegalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByCnpj(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByFantasyName(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByLegalName(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByStateRegistration(Mockito.any())).thenReturn(false);

        Mockito.when(this.passwordEncryptorProvider.encode(Mockito.any())).thenReturn(encodedPassword);

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(this.legalPersonRepository.save(Mockito.any())).thenReturn(legalPerson);

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).registerAfterCommit(Mockito.any());

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                null, null,
                legalPerson.getCnpj(), legalPerson.getFantasyName(), legalPerson.getLegalName(), legalPerson.getStateRegistration()
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        this.createUserUseCase.execute(createUserUseCaseInput);

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByCnpj(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByFantasyName(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByLegalName(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByStateRegistration(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).registerAfterCommit(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when the complement based on a natural person is not sent in the dto")
    public void should_throw_DomainException_when_the_complement_based_on_a_natural_person_is_not_sent_in_the_dto() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                null
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "You should provide naturalPerson when the person type is a NATURAL_PERSON";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw DomainException when the complement based on a legal person is not sent in the dto")
    public void should_throw_DomainException_when_the_complement_based_on_a_legal_person_is_not_sent_in_the_dto() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                null
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "You should provide legalPerson when the person type is a LEGAL_PERSON";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw DomainException when email is already being used")
    public void should_throw_DomainException_when_email_is_already_being_used() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final NaturalPerson naturalPerson = NaturalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(true);

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                naturalPerson.getCpf(), naturalPerson.getGeneralRegister(),
                null, null, null, null
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "This email is already being used";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when cpf is already being used")
    public void should_throw_DomainException_when_cpf_is_already_being_used() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final NaturalPerson naturalPerson = NaturalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.naturalPersonRepository.existsByCpf(Mockito.any())).thenReturn(true);

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                naturalPerson.getCpf(), naturalPerson.getGeneralRegister(),
                null, null, null, null
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "This cpf is already being used";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).existsByCpf(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when general register is already being used")
    public void should_throw_DomainException_when_general_register_is_already_being_used() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final NaturalPerson naturalPerson = NaturalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.naturalPersonRepository.existsByCpf(Mockito.any())).thenReturn(false);

        Mockito.when(this.naturalPersonRepository.existsByGeneralRegister(Mockito.any())).thenReturn(true);

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                naturalPerson.getCpf(), naturalPerson.getGeneralRegister(),
                null, null, null, null
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "This general register is already being used";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).existsByCpf(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).existsByGeneralRegister(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when cnpj is already being used")
    public void should_throw_DomainException_when_cnpj_is_already_being_used() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );
        final LegalPerson legalPerson = LegalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByCnpj(Mockito.any())).thenReturn(true);

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                null, null,
                legalPerson.getCnpj(), legalPerson.getFantasyName(), legalPerson.getLegalName(), legalPerson.getStateRegistration()
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "This cnpj is already being used";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByCnpj(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when fantasy name is already being used")
    public void should_throw_DomainException_when_fantasy_name_is_already_being_used() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );
        final LegalPerson legalPerson = LegalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByCnpj(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByFantasyName(Mockito.any())).thenReturn(true);

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                null, null,
                legalPerson.getCnpj(), legalPerson.getFantasyName(), legalPerson.getLegalName(), legalPerson.getStateRegistration()
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "This fantasy name is already being used";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByCnpj(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByFantasyName(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when legal name is already being used")
    public void should_throw_DomainException_when_legal_name_is_already_being_used() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );
        final LegalPerson legalPerson = LegalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByCnpj(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByFantasyName(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByLegalName(Mockito.any())).thenReturn(true);

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                null, null,
                legalPerson.getCnpj(), legalPerson.getFantasyName(), legalPerson.getLegalName(), legalPerson.getStateRegistration()
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "This legal name is already being used";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByCnpj(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByFantasyName(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByLegalName(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when state registration is already being used")
    public void should_throw_DomainException_when_state_registration_is_already_being_used() {
        final String encodedPassword = "encoded password";
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );
        final LegalPerson legalPerson = LegalPersonBuilderTest.create(user.getId());

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByCnpj(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByFantasyName(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByLegalName(Mockito.any())).thenReturn(false);

        Mockito.when(this.legalPersonRepository.existsByStateRegistration(Mockito.any())).thenReturn(true);

        final PersonComplementInput personComplementInput = PersonComplementInput.with(
                null, null,
                legalPerson.getCnpj(), legalPerson.getFantasyName(), legalPerson.getLegalName(), legalPerson.getStateRegistration()
        );

        final CreateUserUseCaseInput createUserUseCaseInput = CreateUserUseCaseInput.with(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encodedPassword,
                user.getPhoneNumber(),
                user.getPersonType(),
                personComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createUserUseCase.execute(createUserUseCaseInput)
        );

        final String expectedErrorMessage = "This state registration is already being used";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByCnpj(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByFantasyName(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByLegalName(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).existsByStateRegistration(Mockito.any());
    }
}
