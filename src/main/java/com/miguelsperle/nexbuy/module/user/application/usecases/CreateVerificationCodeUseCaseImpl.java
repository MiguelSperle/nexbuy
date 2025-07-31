package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

public class CreateVerificationCodeUseCaseImpl implements CreateVerificationCodeUseCase {
    private final UserCodeRepository userCodeRepository;
    private final CodeProvider codeProvider;
    private final DomainEventPublisherProvider domainEventPublisherProvider;
    private final TransactionExecutor transactionExecutor;

    public CreateVerificationCodeUseCaseImpl(
            UserCodeRepository userCodeRepository,
            CodeProvider codeProvider,
            DomainEventPublisherProvider domainEventPublisherProvider,
            TransactionExecutor transactionExecutor
    ) {
        this.userCodeRepository = userCodeRepository;
        this.codeProvider = codeProvider;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(CreateVerificationCodeUseCaseInput createVerificationCodeUseCaseInput) {
        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(createVerificationCodeUseCaseInput.userId(), codeGenerated, CodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        this.transactionExecutor.registerAfterCommit(() -> this.domainEventPublisherProvider.publishEvent(UserCodeCreatedEvent.from(
                savedUserCode.getId()
        )));
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}