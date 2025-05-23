package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailUserVerificationCodeListener {
    private final IEmailService emailService;

    @Async
    @EventListener
    public void handleUserVerificationCodeCreatedEvent(UserVerificationCodeCreatedEvent userVerificationCodeCreatedEvent) {
        final String message = "Hello, your verification code is " + userVerificationCodeCreatedEvent.getCode() + " and it will expire in 15 minutes";

        this.emailService.sendEmail(userVerificationCodeCreatedEvent.getEmail(), message, "User Verification Code");
    }
}
