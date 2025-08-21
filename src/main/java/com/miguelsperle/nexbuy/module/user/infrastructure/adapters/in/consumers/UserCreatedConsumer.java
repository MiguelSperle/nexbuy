package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCreatedEvent;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.EmailService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreatedConsumer {
    private final UserCodeRepository userCodeRepository;
    private final CodeProvider codeProvider;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TransactionExecutor transactionExecutor;

    private static final String USER_CREATED_QUEUE = "user.created.queue";

    @RabbitListener(queues = USER_CREATED_QUEUE)
    public void onMessage(UserCreatedEvent userCreatedEvent) {
        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(userCreatedEvent.id(), codeGenerated, UserCodeType.USER_VERIFICATION);

        this.transactionExecutor.runTransaction(() -> {
            final UserCode savedUserCode = this.saveUserCode(newUserCode);

            final String text = "Hello, your verification code is " + savedUserCode.getCode() + " and it will expire in 15 minutes";
            final String subject = "User Verification Code";

            final User user = this.getUserById(savedUserCode.getUserId());

            this.emailService.sendEmail(user.getEmail(), text, subject);
        });
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeRepository.findByUserIdAndCodeType(userId, UserCodeType.USER_VERIFICATION.name());
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
