package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResendVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.CreatePasswordResetCodeRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.ResendVerificationCodeRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.ValidatePasswordResetCodeRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.ValidatePasswordResetCodeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-codes")
@RequiredArgsConstructor
public class UserCodeController {
    private final ResendVerificationCodeUseCase resendVerificationCodeUseCase;
    private final CreatePasswordResetCodeUseCase createPasswordResetCodeUseCase;
    private final ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase;

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

    @GetMapping("/password-recovery/{code}/validation")
    public ResponseEntity<ValidatePasswordResetCodeResponse> validatePasswordResetCode(@PathVariable String code) {
        final ValidatePasswordResetCodeUseCaseOutput validatePasswordResetCodeUseCaseOutput =
                this.validatePasswordResetCodeUseCase.execute(ValidatePasswordResetCodeUseCaseInput.with(code));

        return ResponseEntity.ok().body(ValidatePasswordResetCodeResponse.from(validatePasswordResetCodeUseCaseOutput));
    }
}
