package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserPasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserPasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateUserPasswordResetCodeUseCase implements ICreateUserPasswordResetCodeUseCase {
    private final IUserCodeGateway userCodeGateway;
    private final IUserGateway userGateway;
    private final ICodeProvider codeProvider;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;

    @Override
    public void execute(CreateUserPasswordResetCodeUseCaseInput createUserPasswordResetCodeUseCaseInput) {
        final User user = this.getUserByEmail(createUserPasswordResetCodeUseCaseInput.getEmail());

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.userCodeGateway.deleteById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(user, codeGenerated, CodeType.PASSWORD_RESET);

        final UserCode savedUserCode = this.userCodeGateway.save(newUserCode);

        this.domainEventPublisherProvider.publishEvent(new UserCodeCreatedEvent(
                savedUserCode.getUser().getEmail(),
                savedUserCode.getCode(),
                savedUserCode.getCodeType()
        ));
    }

    private User getUserByEmail(String email) {
        return this.userGateway.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeGateway.findByUserIdAndCodeType(userId, CodeType.PASSWORD_RESET.name());
    }
}
