package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResetUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserCodeBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
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
public class ResetUserPasswordUseCaseTest {
    @InjectMocks
    private ResetUserPasswordUseCaseImpl resetUserPasswordUseCase;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptorProvider passwordEncryptorProvider;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    @DisplayName("Should reset user password")
    public void should_reset_user_password() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));
        final String encodedPassword = "encoded password";

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.when(this.passwordEncryptorProvider.encode(Mockito.any())).thenReturn(encodedPassword);

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        final User updatedUser = user.withPassword(encodedPassword);

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(updatedUser);

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final ResetUserPasswordUseCaseInput resetUserPasswordUseCaseInput = ResetUserPasswordUseCaseInput.with(
                userCode.getCode(), user.getPassword()
        );

        this.resetUserPasswordUseCase.execute(resetUserPasswordUseCaseInput);

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user code does not exist")
    public void should_throw_NotFoundException_when_user_code_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        final ResetUserPasswordUseCaseInput resetUserPasswordUseCaseInput = ResetUserPasswordUseCaseInput.with(
                userCode.getCode(), user.getPassword()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.resetUserPasswordUseCase.execute(resetUserPasswordUseCaseInput)
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
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().minusDays(1));

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final ResetUserPasswordUseCaseInput resetUserPasswordUseCaseInput = ResetUserPasswordUseCaseInput.with(
                userCode.getCode(), user.getPassword()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.resetUserPasswordUseCase.execute(resetUserPasswordUseCaseInput)
        );

        final String expectedErrorMessage = "User code has expired";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    public void should_throw_NotFoundException_when_user_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));
        final String encodedPassword = "encoded password";

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.when(this.passwordEncryptorProvider.encode(Mockito.any())).thenReturn(encodedPassword);

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final ResetUserPasswordUseCaseInput resetUserPasswordUseCaseInput = ResetUserPasswordUseCaseInput.with(
                userCode.getCode(), user.getPassword()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.resetUserPasswordUseCase.execute(resetUserPasswordUseCaseInput)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.passwordEncryptorProvider, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
