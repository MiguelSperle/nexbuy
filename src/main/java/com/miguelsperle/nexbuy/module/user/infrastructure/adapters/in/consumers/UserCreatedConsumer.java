package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateVerificationCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCreatedEvent;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.EmailService;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedConsumer {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final CreateVerificationCodeUseCase createVerificationCodeUseCase;

    private static final String USER_CREATED_QUEUE = "user.created.queue";

    @RabbitListener(queues = USER_CREATED_QUEUE)
    public void onMessage(UserCreatedEvent userCreatedEvent) {
        final CreateVerificationCodeUseCaseOutput createVerificationCodeUseCaseOutput =
                this.createVerificationCodeUseCase.execute(CreateVerificationCodeUseCaseInput.with(userCreatedEvent.id()));

        final UserCode userCode = createVerificationCodeUseCaseOutput.userCode();

        final String text = "Hello, your verification code is " + userCode.getCode() + " and it will expire in 15 minutes";
        final String subject = "User Verification Code";

        final User user = this.getUserById(userCode.getUserId());

        this.emailService.sendEmail(user.getEmail(), text, subject);
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
