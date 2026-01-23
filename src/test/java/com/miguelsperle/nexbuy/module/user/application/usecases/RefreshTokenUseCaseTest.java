package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.RefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.RefreshTokenBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.JwtService;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
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
public class RefreshTokenUseCaseTest {
    @InjectMocks
    private RefreshTokenUseCaseImpl refreshTokenUseCase;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should refresh token")
    public void should_refresh_token() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final RefreshToken refreshToken = RefreshTokenBuilderTest.create(user.getId(), TimeUtils.now().plusDays(15));
        final String jwt = "json-web-token";

        Mockito.when(this.refreshTokenRepository.findByToken(Mockito.any())).thenReturn(Optional.of(refreshToken));

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.jwtService.generateJwt(Mockito.any(), Mockito.any())).thenReturn(jwt);

        final RefreshTokenUseCaseInput refreshTokenUseCaseInput = RefreshTokenUseCaseInput.with(refreshToken.getToken());

        final RefreshTokenUseCaseOutput refreshTokenUseCaseOutput = this.refreshTokenUseCase.execute(refreshTokenUseCaseInput);

        Assertions.assertNotNull(refreshTokenUseCaseOutput);
        Assertions.assertNotNull(refreshTokenUseCaseOutput.accessToken());

        Assertions.assertEquals(jwt, refreshTokenUseCaseOutput.accessToken());

        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).findByToken(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.jwtService, Mockito.times(1)).generateJwt(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when refresh token does not exist")
    public void should_throw_NotFoundException_when_refresh_token_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final RefreshToken refreshToken = RefreshTokenBuilderTest.create(user.getId(), TimeUtils.now().plusDays(15));

        Mockito.when(this.refreshTokenRepository.findByToken(Mockito.any())).thenReturn(Optional.empty());

        final RefreshTokenUseCaseInput refreshTokenUseCaseInput = RefreshTokenUseCaseInput.with(refreshToken.getToken());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.refreshTokenUseCase.execute(refreshTokenUseCaseInput)
        );

        final String expectedErrorMessage = "Refresh token not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).findByToken(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when refresh token is expired")
    public void should_throw_DomainException_when_refresh_token_is_expired() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final RefreshToken refreshToken = RefreshTokenBuilderTest.create(user.getId(), TimeUtils.now().minusDays(1));

        Mockito.when(this.refreshTokenRepository.findByToken(Mockito.any())).thenReturn(Optional.of(refreshToken));

        Mockito.doNothing().when(this.refreshTokenRepository).deleteById(Mockito.any());

        final RefreshTokenUseCaseInput refreshTokenUseCaseInput = RefreshTokenUseCaseInput.with(refreshToken.getToken());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.refreshTokenUseCase.execute(refreshTokenUseCaseInput)
        );

        final String expectedErrorMessage = "Refresh token has expired";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    public void should_throw_NotFoundException_when_user_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final RefreshToken refreshToken = RefreshTokenBuilderTest.create(user.getId(), TimeUtils.now().plusDays(15));

        Mockito.when(this.refreshTokenRepository.findByToken(Mockito.any())).thenReturn(Optional.of(refreshToken));

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final RefreshTokenUseCaseInput refreshTokenUseCaseInput = RefreshTokenUseCaseInput.with("refresh-token-non-existent");

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.refreshTokenUseCase.execute(refreshTokenUseCaseInput)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.refreshTokenRepository, Mockito.times(1)).findByToken(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
