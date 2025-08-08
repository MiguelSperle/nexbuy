package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

import java.util.Optional;

public class CreatePasswordResetCodeUseCaseImpl implements CreatePasswordResetCodeUseCase {
    private final UserCodeRepository userCodeRepository;
    private final UserRepository userRepository;
    private final CodeProvider codeProvider;
    private final DomainEventPublisherProvider domainEventPublisherProvider;

    public CreatePasswordResetCodeUseCaseImpl(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            CodeProvider codeProvider,
            DomainEventPublisherProvider domainEventPublisherProvider
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

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, UserCodeType.PASSWORD_RESET);

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
        return this.userCodeRepository.findByUserIdAndCodeType(userId, UserCodeType.PASSWORD_RESET.name());
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}
