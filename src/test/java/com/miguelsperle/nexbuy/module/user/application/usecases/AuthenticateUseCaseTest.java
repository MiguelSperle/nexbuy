package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.RefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.AuthenticateUseCaseOutput;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.mocks.RefreshTokenMock;
import com.miguelsperle.nexbuy.module.user.utils.mocks.UserMock;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.JwtService;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
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
public class AuthenticateUseCaseTest {
    @InjectMocks
    private AuthenticateUseCaseImpl authenticateUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptorProvider passwordEncryptorProvider;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("Should be able to authenticate user")
    public void should_be_able_to_authenticate_user() {
        final User user = UserMock.user().withUserStatus(UserStatus.VERIFIED);
        final String fakeJwtTest = "fake-jwt-test";

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.passwordEncryptorProvider.matches(Mockito.any(), Mockito.any())).thenReturn(true);

        Mockito.when(this.jwtService.generateJwt(Mockito.any(), Mockito.any())).thenReturn(fakeJwtTest);

        Mockito.when(this.refreshTokenRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());

        Mockito.when(this.refreshTokenRepository.save(Mockito.any())).thenReturn(RefreshTokenMock.refreshToken());

        final AuthenticateUseCaseInput authenticateUseCaseInput = AuthenticateUseCaseInput.with(
                user.getEmail(), user.getPassword()
        );

        final AuthenticateUseCaseOutput authenticateUseCaseOutput = this.authenticateUseCase.execute(authenticateUseCaseInput);

        Assertions.assertNotNull(authenticateUseCaseOutput);
        Assertions.assertNotNull(authenticateUseCaseOutput.accessToken());
        Assertions.assertNotNull(authenticateUseCaseOutput.refreshToken());

        Assertions.assertEquals(fakeJwtTest, authenticateUseCaseOutput.accessToken());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
        Mockito.verify(this.jwtService, Mockito.times(1)).generateJwt(Mockito.any(), Mockito.any());
        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).findByUserId(Mockito.any());
        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should be able to authenticate user again but deleting existing refresh token and creating a new one")
    public void should_be_able_to_authenticate_user_again_but_deleting_existing_refresh_token_and_creating_a_new_one() {
        final User user = UserMock.user().withUserStatus(UserStatus.VERIFIED);
        final String fakeJwtTest = "fake-jwt-test";

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.passwordEncryptorProvider.matches(Mockito.any(), Mockito.any())).thenReturn(true);

        Mockito.when(this.jwtService.generateJwt(Mockito.any(), Mockito.any())).thenReturn(fakeJwtTest);

        Mockito.when(this.refreshTokenRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(RefreshTokenMock.refreshToken()));

        Mockito.doNothing().when(this.refreshTokenRepository).deleteById(Mockito.any());

        Mockito.when(this.refreshTokenRepository.save(Mockito.any())).thenReturn(RefreshTokenMock.refreshToken());

        final AuthenticateUseCaseInput authenticateUseCaseInput = AuthenticateUseCaseInput.with(
                user.getEmail(), user.getPassword()
        );

        final AuthenticateUseCaseOutput authenticateUseCaseOutput = this.authenticateUseCase.execute(authenticateUseCaseInput);

        Assertions.assertNotNull(authenticateUseCaseOutput);
        Assertions.assertNotNull(authenticateUseCaseOutput.accessToken());
        Assertions.assertNotNull(authenticateUseCaseOutput.refreshToken());

        Assertions.assertEquals(fakeJwtTest, authenticateUseCaseOutput.accessToken());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
        Mockito.verify(this.jwtService, Mockito.times(1)).generateJwt(Mockito.any(), Mockito.any());
        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).findByUserId(Mockito.any());
        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should not be able to authenticate user due to wrong email")
    public void should_not_be_able_to_authenticate_user_due_to_wrong_email() {
        final User user = UserMock.user();

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        final AuthenticateUseCaseInput authenticateUseCaseInput = AuthenticateUseCaseInput.with(
                user.getEmail(), user.getPassword()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.authenticateUseCase.execute(authenticateUseCaseInput)
        );

        final String expectedErrorMessage = "Wrong credentials";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    @DisplayName("Should not be able to authenticate user due to wrong password")
    public void should_not_be_able_to_authenticate_user_due_to_wrong_password() {
        final User user = UserMock.user().withUserStatus(UserStatus.VERIFIED);

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.passwordEncryptorProvider.matches(Mockito.any(), Mockito.any())).thenReturn(false);

        final AuthenticateUseCaseInput authenticateUseCaseInput = AuthenticateUseCaseInput.with(
                user.getEmail(), user.getPassword()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.authenticateUseCase.execute(authenticateUseCaseInput)
        );

        final String expectedErrorMessage = "Wrong credentials";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should not be able to authenticate user because they are not verified")
    public void should_not_be_able_to_authenticate_user_because_they_are_not_verified() {
        final User user = UserMock.user();

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.passwordEncryptorProvider.matches(Mockito.any(), Mockito.any())).thenReturn(true);

        final AuthenticateUseCaseInput authenticateUseCaseInput = AuthenticateUseCaseInput.with(
                user.getEmail(), user.getPassword()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.authenticateUseCase.execute(authenticateUseCaseInput)
        );

        final String expectedErrorMessage = "User not verified";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should not be able to authenticate user because they are deleted")
    public void should_not_be_able_to_authenticate_user_because_they_are_deleted() {
        final User user = UserMock.user().withUserStatus(UserStatus.DELETED);

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.passwordEncryptorProvider.matches(Mockito.any(), Mockito.any())).thenReturn(true);

        final AuthenticateUseCaseInput authenticateUseCaseInput = AuthenticateUseCaseInput.with(
                user.getEmail(), user.getPassword()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.authenticateUseCase.execute(authenticateUseCaseInput)
        );

        final String expectedErrorMessage = "User has been deleted and cannot authenticate";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }
}
