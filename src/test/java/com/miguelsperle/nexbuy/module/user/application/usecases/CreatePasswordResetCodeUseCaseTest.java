package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.UserCodeBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
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
public class CreatePasswordResetCodeUseCaseTest {
    @InjectMocks
    private CreatePasswordResetCodeUseCaseImpl createPasswordResetCodeUseCase;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CodeProvider codeProvider;

    @Mock
    private MessageProducer messageProducer;

    @Test
    @DisplayName("Should create password reset code")
    public void should_create_password_reset_code() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(userCode.getCode());

        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenReturn(userCode);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final CreatePasswordResetCodeUseCaseInput createPasswordResetCodeUseCaseInput = CreatePasswordResetCodeUseCaseInput.with(user.getEmail());

        this.createPasswordResetCodeUseCase.execute(createPasswordResetCodeUseCaseInput);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should create password reset code again but deleting existing password reset code")
    public void should_create_password_reset_code_again_but_deleting_existing_password_reset_code() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final UserCode userCode = UserCodeBuilderTest.create(user.getId(), UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(userCode.getCode());

        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenReturn(userCode);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final CreatePasswordResetCodeUseCaseInput createPasswordResetCodeUseCaseInput = CreatePasswordResetCodeUseCaseInput.with(
                user.getEmail()
        );

        this.createPasswordResetCodeUseCase.execute(createPasswordResetCodeUseCaseInput);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user email does not exist")
    public void should_throw_NotFoundException_when_user_email_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        final CreatePasswordResetCodeUseCaseInput createPasswordResetCodeUseCaseInput = CreatePasswordResetCodeUseCaseInput.with(
                user.getEmail()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.createPasswordResetCodeUseCase.execute(createPasswordResetCodeUseCaseInput)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }
}
