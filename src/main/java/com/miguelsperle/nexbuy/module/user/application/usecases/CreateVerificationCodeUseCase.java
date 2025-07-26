package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

public class CreateVerificationCodeUseCase implements ICreateVerificationCodeUseCase {
    private final IUserCodeRepository userCodeRepository;
    private final ICodeProvider codeProvider;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ITransactionExecutor transactionExecutor;

    public CreateVerificationCodeUseCase(
            IUserCodeRepository userCodeRepository,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ITransactionExecutor transactionExecutor
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