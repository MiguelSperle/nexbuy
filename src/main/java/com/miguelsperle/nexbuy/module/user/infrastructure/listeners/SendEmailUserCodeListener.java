package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
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
    private final IUserCodeGateway userCodeGateway;
    private final IUserGateway userGateway;

    @Async
    @EventListener
    public void handleUserCodeCreatedEvent(UserCodeCreatedEvent userCodeCreatedEvent) {
        final UserCode userCode = this.getUserCodeById(userCodeCreatedEvent.id());

        final String message = userCode.getCodeType() == CodeType.USER_VERIFICATION
                ? "Hello, your verification code is " + userCode.getCode() + " and it will expire in 15 minutes."
                : "Hello, your password reset code is " + userCode.getCode() + " and it will expire in 15 minutes.";

        final String subject = userCode.getCodeType() == CodeType.USER_VERIFICATION ? "User Verification Code" : "Password Reset Code";

        final User user = this.getUserById(userCode.getUserId());

        this.emailService.sendEmail(user.getEmail(), message, subject);
    }

    private UserCode getUserCodeById(String userCodeId) {
        return this.userCodeGateway.findById(userCodeId)
                .orElseThrow(() -> new UserCodeNotFoundException("User code not found"));
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
