package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserCodeBuilderTest;
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
public class UpdateUserToVerifiedUseCaseTest {
    @InjectMocks
    private UpdateUserToVerifiedUseCaseImpl updateUserToVerifiedUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    @DisplayName("Should update user to verified")
    public void should_update_user_to_verified() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.USER_VERIFICATION, TimeUtils.now().plusMinutes(15));

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        final User updatedUser = user.withUserStatus(UserStatus.VERIFIED);

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(updatedUser);

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput = UpdateUserToVerifiedUseCaseInput.with(
                userCode.getCode()
        );

        this.updateUserToVerifiedUseCase.execute(updateUserToVerifiedUseCaseInput);

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user code does not exist")
    public void should_throw_NotFoundException_when_user_code_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.USER_VERIFICATION, TimeUtils.now().plusMinutes(15));

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        final UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput = UpdateUserToVerifiedUseCaseInput.with(
                userCode.getCode()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateUserToVerifiedUseCase.execute(updateUserToVerifiedUseCaseInput)
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
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.USER_VERIFICATION, TimeUtils.now().minusDays(1));

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput = UpdateUserToVerifiedUseCaseInput.with(
                userCode.getCode()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateUserToVerifiedUseCase.execute(updateUserToVerifiedUseCaseInput)
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
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.USER_VERIFICATION, TimeUtils.now().plusMinutes(15));

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput = UpdateUserToVerifiedUseCaseInput.with(
                userCode.getCode()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateUserToVerifiedUseCase.execute(updateUserToVerifiedUseCaseInput)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
