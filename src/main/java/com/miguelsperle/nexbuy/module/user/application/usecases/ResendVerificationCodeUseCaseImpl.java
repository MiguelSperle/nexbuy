package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResendVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserAlreadyVerifiedException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserDeletedException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

import java.util.Optional;

public class ResendVerificationCodeUseCaseImpl implements ResendVerificationCodeUseCase {
    private final UserCodeRepository userCodeRepository;
    private final UserRepository userRepository;
    private final DomainEventPublisherProvider domainEventPublisherProvider;
    private final CodeProvider codeProvider;

    public ResendVerificationCodeUseCaseImpl(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            DomainEventPublisherProvider domainEventPublisherProvider,
            CodeProvider codeProvider
    ) {
        this.userCodeRepository = userCodeRepository;
        this.userRepository = userRepository;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
        this.codeProvider = codeProvider;
    }

    @Override
    public void execute(ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput) {
        final User user = this.getUserByEmail(resendVerificationCodeUseCaseInput.email());

        if (user.getUserStatus() == UserStatus.VERIFIED) {
            throw UserAlreadyVerifiedException.with("User already verified");
        }

        if (user.getUserStatus() == UserStatus.DELETED) {
            throw UserDeletedException.with("User has been deleted and cannot ask for resending");
        }

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, UserCodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        this.domainEventPublisherProvider.publishEvent(UserCodeCreatedEvent.from(
                savedUserCode.getCode(),
                savedUserCode.getUserCodeType(),
                savedUserCode.getUserId()
        ));
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeRepository.findByUserIdAndCodeType(userId, UserCodeType.USER_VERIFICATION.name());
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }
}
