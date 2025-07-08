package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailUserCodeListener {
    private final IEmailService emailService;

    @Async
    @EventListener
    public void handleUserCodeCreatedEvent(UserCodeCreatedEvent userCodeCreatedEvent) {
        final String message = userCodeCreatedEvent.codeType() == CodeType.USER_VERIFICATION
                ? "Hello, your verification code is " + userCodeCreatedEvent.code() + " and it will expire in 15 minutes."
                : "Hello, your password reset code is " + userCodeCreatedEvent.code() + " and it will expire in 15 minutes.";

        final String subject = userCodeCreatedEvent.codeType() == CodeType.USER_VERIFICATION ? "User Verification Code" : "Password Reset Code";

        this.emailService.sendEmail(userCodeCreatedEvent.email(), message, subject);
    }
}
