package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SendEmailUserVerificationCodeListener {
    private final IEmailService emailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserVerificationCodeCreatedEventInTransactional(UserVerificationCodeCreatedEvent userVerificationCodeCreatedEvent) {
        System.out.println("aq");
        final String message = "Hello, your verification code is " + userVerificationCodeCreatedEvent.getCode();

        this.emailService.sendEmail(userVerificationCodeCreatedEvent.getEmail(), message, "User Verification Code");
    }
}
