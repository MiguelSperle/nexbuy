package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserVerificationCodeCreatedListener {
    private final IEmailService emailService;

    @Async
    @EventListener
    public void handleUserVerificationCodeCreatedEvent(UserVerificationCodeCreatedEvent userVerificationCodeCreatedEvent) {
        final String userEmail = userVerificationCodeCreatedEvent.getUserVerificationCode().getUser().getEmail();
        final String code = userVerificationCodeCreatedEvent.getUserVerificationCode().getCode();

        final String message = "Hello, your verification code is " + code;

        this.emailService.sendEmail(userEmail, message, "User Verification Code");
    }
}
