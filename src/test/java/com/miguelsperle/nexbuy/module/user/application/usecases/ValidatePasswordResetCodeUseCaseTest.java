package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserCodeBuilderTest;
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
public class ValidatePasswordResetCodeUseCaseTest {
    @InjectMocks
    private ValidatePasswordResetCodeUseCaseImpl validatePasswordResetCodeUseCase;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Test
    @DisplayName("Should validate password reset code")
    public void should_validate_password_reset_code() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(
                user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15)
        );

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        final ValidatePasswordResetCodeUseCaseInput validatePasswordResetCodeUseCaseInput = ValidatePasswordResetCodeUseCaseInput.with(userCode.getCode());

        this.validatePasswordResetCodeUseCase.execute(validatePasswordResetCodeUseCaseInput);

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user code does not exist")
    public void should_throw_NotFoundException_when_user_code_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(
                user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15)
        );

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        final ValidatePasswordResetCodeUseCaseInput validatePasswordResetCodeUseCaseInput = ValidatePasswordResetCodeUseCaseInput.with(
                userCode.getCode()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.validatePasswordResetCodeUseCase.execute(validatePasswordResetCodeUseCaseInput)
        );

        final String expectedErrorMessage = "User code not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when user code is expired")
    public void should_throw_DomainException_when_user_code_is_expired() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(
                user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().minusDays(1)
        );

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final ValidatePasswordResetCodeUseCaseInput validatePasswordResetCodeUseCaseInput = ValidatePasswordResetCodeUseCaseInput.with(
                userCode.getCode()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.validatePasswordResetCodeUseCase.execute(validatePasswordResetCodeUseCaseInput)
        );

        final String expectedErrorMessage = "User code has expired";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }
}
