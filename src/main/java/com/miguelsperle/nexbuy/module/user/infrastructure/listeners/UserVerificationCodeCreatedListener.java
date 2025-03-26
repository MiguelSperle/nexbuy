package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserVerificationCodeCreatedListener implements ApplicationListener<UserVerificationCodeCreatedEvent> {
    private final IEmailService emailService;

    @Async
    @Override
    public void onApplicationEvent(UserVerificationCodeCreatedEvent userVerificationCodeCreatedEvent) {
        final String userEmail = userVerificationCodeCreatedEvent.getUserVerificationCode().getUser().getEmail();
        final String code = userVerificationCodeCreatedEvent.getUserVerificationCode().getCode();

        final String message = "Hello, your verification code is " + code;

        this.emailService.sendEmail(userEmail, message, "User Verification Code");
    }
}
