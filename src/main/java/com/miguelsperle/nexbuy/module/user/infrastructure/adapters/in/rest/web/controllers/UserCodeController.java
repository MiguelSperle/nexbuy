package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.rest.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResendVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests.CreatePasswordResetCodeRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests.ResendVerificationCodeRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests.ValidatePasswordResetCodeRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.responses.ValidatePasswordResetCodeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-codes")
@RequiredArgsConstructor
public class UserCodeController {
    private final IResendVerificationCodeUseCase resendVerificationCodeUseCase;
    private final ICreatePasswordResetCodeUseCase createPasswordResetCodeUseCase;
    private final IValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase;

    @PostMapping("/verification/resend")
    public ResponseEntity<MessageResponse> resendVerificationCode(@RequestBody @Valid ResendVerificationCodeRequest resendVerificationCodeRequest) {
        this.resendVerificationCodeUseCase.execute(ResendVerificationCodeUseCaseInput.with(
                resendVerificationCodeRequest.email()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Verification code sent successfully"));
    }

    @PostMapping("/password-recovery")
    public ResponseEntity<MessageResponse> createPasswordResetCode(@RequestBody @Valid CreatePasswordResetCodeRequest createPasswordResetCodeRequest) {
        this.createPasswordResetCodeUseCase.execute(CreatePasswordResetCodeUseCaseInput.with(
                createPasswordResetCodeRequest.email()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Password reset code sent successfully"));
    }

    @PostMapping("/password-recovery/validation")
    public ResponseEntity<ValidatePasswordResetCodeResponse> validatePasswordResetCode(@RequestBody @Valid ValidatePasswordResetCodeRequest validatePasswordResetCodeRequest) {
        final ValidatePasswordResetCodeUseCaseOutput validatePasswordResetCodeUseCaseOutput = this.validatePasswordResetCodeUseCase.execute(ValidatePasswordResetCodeUseCaseInput.with(
                validatePasswordResetCodeRequest.code()
        ));

        return ResponseEntity.ok().body(ValidatePasswordResetCodeResponse.from(validatePasswordResetCodeUseCaseOutput));
    }
}
