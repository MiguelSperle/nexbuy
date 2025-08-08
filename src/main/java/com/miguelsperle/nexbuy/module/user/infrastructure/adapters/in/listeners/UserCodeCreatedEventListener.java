package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.listeners;

import com.miguelsperle.nexbuy.shared.application.ports.out.services.EmailService;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.exceptions.EventProcessingFailureException;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
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
public class UserCodeCreatedEventListener {
    private final EmailService emailService;
    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserCodeCreatedEventListener.class);

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
    public void handleUserCodeCreatedEvent(UserCodeCreatedEvent userCodeCreatedEvent) {
        try {
            final String message = userCodeCreatedEvent.userCodeType() == UserCodeType.USER_VERIFICATION
                    ? "Hello, your verification code is " + userCodeCreatedEvent.code() + " and it will expire in 15 minutes."
                    : "Hello, your password reset code is " + userCodeCreatedEvent.code() + " and it will expire in 15 minutes.";

            final String subject = userCodeCreatedEvent.userCodeType() == UserCodeType.USER_VERIFICATION ? "User Verification Code" : "Password Reset Code";

            final User user = this.getUserById(userCodeCreatedEvent.userId());

            this.emailService.sendEmail(user.getEmail(), message, subject);
        } catch (Exception ex) {
            log.error("Failed to process event - Type: [{}] - Details: [{}]",
                    userCodeCreatedEvent.getClass().getSimpleName(),
                    userCodeCreatedEvent,
                    ex
            );
            throw EventProcessingFailureException.with("Failed to process event", ex);
        }
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }

    @Recover
    public void recover(EventProcessingFailureException ex, UserCodeCreatedEvent userCodeCreatedEvent) {
        log.error("All retry attempts to process the event failed - Type: [{}] - Details: [{}]",
                userCodeCreatedEvent.getClass().getSimpleName(),
                userCodeCreatedEvent,
                ex
        );
    }
}
