package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
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
public class UpdateUserPasswordUseCaseTest {
    @InjectMocks
    private UpdateUserPasswordUseCaseImpl updateUserPasswordUseCase;

    @Mock
    private SecurityContextService securityContextService;

    @Mock
    private PasswordEncryptorProvider passwordEncryptorProvider;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should update user password")
    public void should_update_user_password() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final String encodedPassword = "encoded password";
        final String newPassword = "batman1234";

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.passwordEncryptorProvider.matches(Mockito.any(), Mockito.any())).thenReturn(true);

        Mockito.when(this.passwordEncryptorProvider.encode(Mockito.any())).thenReturn(encodedPassword);

        final User updatedUser = user.withPassword(encodedPassword);

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(updatedUser);

        final UpdateUserPasswordUseCaseInput updateUserPasswordUseCaseInput = UpdateUserPasswordUseCaseInput.with(
                user.getPassword(), newPassword
        );

        this.updateUserPasswordUseCase.execute(updateUserPasswordUseCaseInput);

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).encode(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    public void should_throw_NotFoundException_when_user_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final String newPassword = "batman1234";

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final UpdateUserPasswordUseCaseInput updateUserPasswordUseCaseInput = UpdateUserPasswordUseCaseInput.with(
                user.getPassword(), newPassword
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateUserPasswordUseCase.execute(updateUserPasswordUseCaseInput)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when current password provided does not match the hashed password in the database")
    public void should_throw_DomainException_when_current_password_provided_does_not_match_the_hashed_password_in_the_database() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final String newPassword = "batman1234";

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.passwordEncryptorProvider.matches(Mockito.any(), Mockito.any())).thenReturn(false);

        final UpdateUserPasswordUseCaseInput updateUserPasswordUseCaseInput = UpdateUserPasswordUseCaseInput.with(
                user.getPassword(), newPassword
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateUserPasswordUseCase.execute(updateUserPasswordUseCaseInput)
        );

        final String expectedErrorMessage = "Invalid current password";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }
}
