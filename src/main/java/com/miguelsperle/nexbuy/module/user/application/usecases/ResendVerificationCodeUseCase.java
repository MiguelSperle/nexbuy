package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResendVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserAlreadyVerifiedException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

import java.util.Optional;

public class ResendVerificationCodeUseCase implements IResendVerificationCodeUseCase {
    private final IUserCodeRepository userCodeRepository;
    private final IUserRepository userRepository;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ICodeProvider codeProvider;

    public ResendVerificationCodeUseCase(
            IUserCodeRepository userCodeRepository,
            IUserRepository userRepository,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ICodeProvider codeProvider
    ) {
        this.userCodeRepository = userCodeRepository;
        this.userRepository = userRepository;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
        this.codeProvider = codeProvider;
    }

    @Override
    public void execute(ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput) {
        final User user = this.getUserByEmail(resendVerificationCodeUseCaseInput.email());

        if (user.getIsVerified()) {
            throw UserAlreadyVerifiedException.with("User already verified");
        }

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, CodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        this.domainEventPublisherProvider.publishEvent(UserCodeCreatedEvent.from(savedUserCode.getId()));
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeRepository.findByUserIdAndCodeType(userId, CodeType.USER_VERIFICATION.name());
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }
}
