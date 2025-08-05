package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.listeners;

import com.miguelsperle.nexbuy.core.application.ports.out.services.EmailService;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.exceptions.EventProcessingFailureException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateVerificationCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCreatedEvent;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedEventListener {
    private final CreateVerificationCodeUseCase createVerificationCodeUseCase;
    private final UserRepository userRepository;
    private final EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(UserCreatedEventListener.class);

    @Async
    @EventListener
    @Retryable(
            retryFor = {EventProcessingFailureException.class},
            maxAttempts = 4,
            backoff = @Backoff( // Exponential Backoff Configuration
                    delay = 3000,
                    multiplier = 2,
                    maxDelay = 24000
            )
    )
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        try {
            final CreateVerificationCodeUseCaseOutput createVerificationCodeUseCaseOutput =
                    this.createVerificationCodeUseCase.execute(CreateVerificationCodeUseCaseInput.with(userCreatedEvent.id()));

            final UserCode userCode = createVerificationCodeUseCaseOutput.userCode();

            final String message = "Hello, your verification code is " + userCode.getCode() + " and it will expire in 15 minutes";
            final String subject = "User Verification Code";

            final User user = this.getUserById(userCode.getUserId());

            this.emailService.sendEmail(user.getEmail(), message, subject);
        } catch (Exception exception) {
            log.error("Failed to process event - Type: [{}] - Details: [{}]",
                    userCreatedEvent.getClass().getSimpleName(),
                    userCreatedEvent,
                    exception
            );
            throw EventProcessingFailureException.with("Failed to process event", exception);
        }
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    @Recover
    public void recover(EventProcessingFailureException eventProcessingFailureException, UserCreatedEvent userCreatedEvent) {
        log.error("All retry attempts to process the event failed - Type: [{}] - Details: [{}]",
                userCreatedEvent.getClass().getSimpleName(),
                userCreatedEvent,
                eventProcessingFailureException
        );
    }
}