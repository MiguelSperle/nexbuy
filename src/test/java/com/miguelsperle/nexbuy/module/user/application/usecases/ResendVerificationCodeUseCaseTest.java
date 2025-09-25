package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResendVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserCodeBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
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
public class ResendVerificationCodeUseCaseTest {
    @InjectMocks
    private ResendVerificationCodeUseCaseImpl resendVerificationCodeUseCase;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private CodeProvider codeProvider;

    @Test
    @DisplayName("Should resend verification code")
    public void should_resend_verification_code() {
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.USER_VERIFICATION);

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(userCode.getCode());

        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenReturn(userCode);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        this.resendVerificationCodeUseCase.execute(resendVerificationCodeUseCaseInput);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should resend verification code but deleting existing verification code")
    public void should_resend_verification_code_but_deleting_existing_verification_code() {
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.USER_VERIFICATION);

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(userCode.getCode());

        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenReturn(userCode);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        this.resendVerificationCodeUseCase.execute(resendVerificationCodeUseCaseInput);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    public void should_throw_NotFoundException_when_user_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.UNVERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        final ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.resendVerificationCodeUseCase.execute(resendVerificationCodeUseCaseInput)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when user is verified")
    public void should_throw_DomainException_when_user_is_verified() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        final ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.resendVerificationCodeUseCase.execute(resendVerificationCodeUseCaseInput)
        );

        final String expectedErrorMessage = "User already verified";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when user is deleted")
    public void should_throw_DomainException_when_user_is_deleted() {
        final User user = UserBuilderTest.create(
                UserStatus.DELETED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        final ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.resendVerificationCodeUseCase.execute(resendVerificationCodeUseCaseInput)
        );

        final String expectedErrorMessage = "User has been deleted and cannot ask for resending";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }
}
