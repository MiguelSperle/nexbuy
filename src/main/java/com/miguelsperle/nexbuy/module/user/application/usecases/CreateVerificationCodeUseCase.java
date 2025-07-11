package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

public class CreateVerificationCodeUseCase implements ICreateVerificationCodeUseCase {
    private final IUserCodeGateway userCodeGateway;
    private final ICodeProvider codeProvider;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ITransactionExecutor transactionExecutor;

    public CreateVerificationCodeUseCase(
            IUserCodeGateway userCodeGateway,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ITransactionExecutor transactionExecutor
    ) {
        this.userCodeGateway = userCodeGateway;
        this.codeProvider = codeProvider;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(CreateVerificationCodeUseCaseInput createVerificationCodeUseCaseInput) {
        final User user = createVerificationCodeUseCaseInput.user();

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(user, codeGenerated, CodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        this.transactionExecutor.registerAfterCommit(() -> this.domainEventPublisherProvider.publishEvent(new UserCodeCreatedEvent(
                savedUserCode.getUser().getEmail(),
                savedUserCode.getCode(),
                savedUserCode.getCodeType())
        ));
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeGateway.save(userCode);
    }
}