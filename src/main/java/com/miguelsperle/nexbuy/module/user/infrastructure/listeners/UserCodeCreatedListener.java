package com.miguelsperle.nexbuy.module.user.infrastructure.listeners;

import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.EmailService;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCodeCreatedListener {
    private final EmailService emailService;
    private final UserRepository userRepository;

    private static final String USER_CODE_CREATED_QUEUE = "user.code.created.queue";

    @RabbitListener(queues = USER_CODE_CREATED_QUEUE)
    public void onMessage(UserCodeCreatedEvent userCodeCreatedEvent) {
        final String text = userCodeCreatedEvent.userCodeType() == UserCodeType.USER_VERIFICATION
                ? "Hello, your verification code is " + userCodeCreatedEvent.code() + " and it will expire in 15 minutes."
                : "Hello, your password reset code is " + userCodeCreatedEvent.code() + " and it will expire in 15 minutes.";

        final String subject = userCodeCreatedEvent.userCodeType() == UserCodeType.USER_VERIFICATION ? "User Verification Code" : "Password Reset Code";

        final User user = this.getUserById(userCodeCreatedEvent.userId());

        this.emailService.sendEmail(user.getEmail(), text, subject);
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
