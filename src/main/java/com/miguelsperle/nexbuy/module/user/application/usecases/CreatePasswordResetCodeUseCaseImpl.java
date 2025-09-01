package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.util.Optional;

public class CreatePasswordResetCodeUseCaseImpl implements CreatePasswordResetCodeUseCase {
    private final UserCodeRepository userCodeRepository;
    private final UserRepository userRepository;
    private final CodeProvider codeProvider;
    private final MessageProducer messageProducer;

    private static final String USER_CODE_CREATED_EXCHANGE = "user.code.created.exchange";
    private static final String USER_CODE_CREATED_ROUTING_KEY = "user.code.created.routing.key";

    private final static int CODE_LENGTH = 6;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public CreatePasswordResetCodeUseCaseImpl(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            CodeProvider codeProvider,
            MessageProducer messageProducer
    ) {
        this.userCodeRepository = userCodeRepository;
        this.userRepository = userRepository;
        this.codeProvider = codeProvider;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(CreatePasswordResetCodeUseCaseInput createPasswordResetCodeUseCaseInput) {
        final User user = this.getUserByEmail(createPasswordResetCodeUseCaseInput.email());

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, UserCodeType.PASSWORD_RESET);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        final UserCodeCreatedEvent userCodeCreatedEvent = UserCodeCreatedEvent.from(
                savedUserCode.getCode(),
                savedUserCode.getUserCodeType(),
                savedUserCode.getUserId()
        );

        this.messageProducer.publish(USER_CODE_CREATED_EXCHANGE, USER_CODE_CREATED_ROUTING_KEY, userCodeCreatedEvent);
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> NotFoundException.with("User not found"));
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
