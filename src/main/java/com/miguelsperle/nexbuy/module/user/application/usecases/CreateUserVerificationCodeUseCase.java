package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserVerificationCodeUseCase implements ICreateUserVerificationCodeUseCase {
    private final IUserCodeGateway userCodeGateway;
    private final ICodeProvider codeProvider;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ITransactionExecutor transactionExecutor;

    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static int CODE_LENGTH = 6;

    @Override
    public void execute(CreateUserVerificationCodeUseCaseInput createUserVerificationCodeUseCaseInput) {
        final User user = createUserVerificationCodeUseCaseInput.getUser();

        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

        final UserCode newUserCode = UserCode.newUserCode(user, codeGenerated, CodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.userCodeGateway.save(newUserCode);

        this.transactionExecutor.registerAfterCommit(() -> this.domainEventPublisherProvider.publishEvent(new UserCodeCreatedEvent(
                savedUserCode.getUser().getEmail(),
                savedUserCode.getCode(),
                savedUserCode.getCodeType())
        ));
    }
}