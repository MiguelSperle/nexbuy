package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

import java.util.Optional;

public class CreatePasswordResetCodeUseCase implements ICreatePasswordResetCodeUseCase {
    private final IUserCodeRepository userCodeRepository;
    private final IUserRepository userRepository;
    private final ICodeProvider codeProvider;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;

    public CreatePasswordResetCodeUseCase(
            IUserCodeRepository userCodeRepository,
            IUserRepository userRepository,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider
    ) {
        this.userCodeRepository = userCodeRepository;
        this.userRepository = userRepository;
        this.codeProvider = codeProvider;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
    }

    @Override
    public void execute(CreatePasswordResetCodeUseCaseInput createPasswordResetCodeUseCaseInput) {
        final User user = this.getUserByEmail(createPasswordResetCodeUseCaseInput.email());

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, CodeType.PASSWORD_RESET);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        this.domainEventPublisherProvider.publishEvent(UserCodeCreatedEvent.from(savedUserCode.getId()));
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeRepository.findByUserIdAndCodeType(userId, CodeType.PASSWORD_RESET.name());
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}
