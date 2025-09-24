package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.LegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.NaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.LegalPersonBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.NaturalPersonBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetAuthenticatedUserUseCaseTest {
    @InjectMocks
    private GetAuthenticatedUserUseCaseImpl getAuthenticatedUserUseCase;

    @Mock
    private SecurityContextService securityContextService;

    @Mock
    private NaturalPersonRepository naturalPersonRepository;

    @Mock
    private LegalPersonRepository legalPersonRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get authenticated user with natural person data")
    public void should_get_authenticated_user_with_natural_person_data() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final NaturalPerson naturalPerson = NaturalPersonBuilderTest.create(user.getId());

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.naturalPersonRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(naturalPerson));

        final GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput = this.getAuthenticatedUserUseCase.execute();

        Assertions.assertNotNull(getAuthenticatedUserUseCaseOutput);
        Assertions.assertNotNull(getAuthenticatedUserUseCaseOutput.authenticatedUser());
        Assertions.assertNotNull(getAuthenticatedUserUseCaseOutput.personComplementOutput());

        Assertions.assertEquals(user, getAuthenticatedUserUseCaseOutput.authenticatedUser());
        Assertions.assertEquals(naturalPerson.getCpf(), getAuthenticatedUserUseCaseOutput.personComplementOutput().cpf());
        Assertions.assertEquals(naturalPerson.getGeneralRegister(), getAuthenticatedUserUseCaseOutput.personComplementOutput().generalRegister());

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).findByUserId(Mockito.any());
    }

    @Test
    @DisplayName("Should get authenticated user with legal person data")
    public void should_get_authenticated_user_with_legal_person_data() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );
        final LegalPerson legalPerson = LegalPersonBuilderTest.create(user.getId());

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.legalPersonRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(legalPerson));

        final GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput = this.getAuthenticatedUserUseCase.execute();

        Assertions.assertNotNull(getAuthenticatedUserUseCaseOutput);
        Assertions.assertNotNull(getAuthenticatedUserUseCaseOutput.authenticatedUser());
        Assertions.assertNotNull(getAuthenticatedUserUseCaseOutput.personComplementOutput());

        Assertions.assertEquals(user, getAuthenticatedUserUseCaseOutput.authenticatedUser());
        Assertions.assertEquals(legalPerson.getCnpj(), getAuthenticatedUserUseCaseOutput.personComplementOutput().cnpj());
        Assertions.assertEquals(legalPerson.getLegalName(), getAuthenticatedUserUseCaseOutput.personComplementOutput().legalName());
        Assertions.assertEquals(legalPerson.getFantasyName(), getAuthenticatedUserUseCaseOutput.personComplementOutput().fantasyName());
        Assertions.assertEquals(legalPerson.getStateRegistration(), getAuthenticatedUserUseCaseOutput.personComplementOutput().stateRegistration());

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).findByUserId(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    public void should_throw_NotFoundException_when_user_does_not_exist() {
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getAuthenticatedUserUseCase.execute()
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when natural person does not exist")
    public void should_throw_NotFoundException_when_natural_person_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.naturalPersonRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getAuthenticatedUserUseCase.execute()
        );

        final String expectedErrorMessage = "Natural person not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.naturalPersonRepository, Mockito.times(1)).findByUserId(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when legal person does not exist")
    public void should_throw_NotFoundException_when_legal_person_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.LEGAL_PERSON
        );

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.legalPersonRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getAuthenticatedUserUseCase.execute()
        );

        final String expectedErrorMessage = "Legal person not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.legalPersonRepository, Mockito.times(1)).findByUserId(Mockito.any());
    }
}
