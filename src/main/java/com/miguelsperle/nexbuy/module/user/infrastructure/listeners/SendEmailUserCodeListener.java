package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.EventProcessingFailureException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
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
public class SendEmailUserCodeListener {
    private final IEmailService emailService;
    private final IUserCodeGateway userCodeGateway;
    private final IUserGateway userGateway;

    private static final Logger log = LoggerFactory.getLogger(SendEmailUserCodeListener.class);

    @Async
    @EventListener
    @Retryable(
            retryFor = {EventProcessingFailureException.class},
            backoff = @Backoff( // Exponential Backoff Configuration
                    delay = 2000, // initial delay
                    multiplier = 2, // multiply by two
                    maxDelay = 10000 // max delay of 10 seconds
            )
    )
    public void handleUserCodeCreatedEvent(UserCodeCreatedEvent userCodeCreatedEvent) {
        try {
            final UserCode userCode = this.getUserCodeById(userCodeCreatedEvent.id());

            final String message = userCode.getCodeType() == CodeType.USER_VERIFICATION
                    ? "Hello, your verification code is " + userCode.getCode() + " and it will expire in 15 minutes."
                    : "Hello, your password reset code is " + userCode.getCode() + " and it will expire in 15 minutes.";

            final String subject = userCode.getCodeType() == CodeType.USER_VERIFICATION ? "User Verification Code" : "Password Reset Code";

            final User user = this.getUserById(userCode.getUserId());

            this.emailService.sendEmail(user.getEmail(), message, subject);
        } catch (UserCodeNotFoundException | UserNotFoundException exception) {
            log.error("Failed to process event - Event: [{}]", userCodeCreatedEvent.getClass().getSimpleName(), exception);
        } catch (Exception exception) {
            log.error("Failed to process event - Event: [{}]", userCodeCreatedEvent.getClass().getSimpleName(), exception);
            throw EventProcessingFailureException.with("Failed to process event", exception);
        }
    }

    private UserCode getUserCodeById(String userCodeId) {
        return this.userCodeGateway.findById(userCodeId)
                .orElseThrow(() -> UserCodeNotFoundException.with("User code not found"));
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    @Recover
    public void recover(EventProcessingFailureException eventProcessingFailureException, UserCodeCreatedEvent userCodeCreatedEvent) {
        log.error("All retry attempts to process the event failed - Event: [{}]",
                userCodeCreatedEvent.getClass().getSimpleName(), eventProcessingFailureException
        );
    }
}
