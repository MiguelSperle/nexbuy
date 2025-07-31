package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.ports.in.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResetUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.ResetUserPasswordRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.UpdateUserPasswordRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.UpdateUserRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.UpdateUserToVerifiedRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.GetAuthenticatedUserLegalPersonResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.GetAuthenticatedUserNaturalPersonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;
    private final ResetUserPasswordUseCase resetUserPasswordUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @PatchMapping("/verification")
    public ResponseEntity<MessageResponse> updateUserToVerified(@RequestBody @Valid UpdateUserToVerifiedRequest updateUserToVerifiedRequest) {
        this.updateUserToVerifiedUseCase.execute(UpdateUserToVerifiedUseCaseInput.with(
                updateUserToVerifiedRequest.code()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("User verified successfully"));
    }

    @PatchMapping("/password-reset")
    public ResponseEntity<MessageResponse> resetUserPassword(@RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        this.resetUserPasswordUseCase.execute(ResetUserPasswordUseCaseInput.with(
                resetUserPasswordRequest.code(), resetUserPasswordRequest.password()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("User password reset successfully"));
    }

    @PatchMapping("/information")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        this.updateUserUseCase.execute(UpdateUserUseCaseInput.with(
                updateUserRequest.firstName(),
                updateUserRequest.lastName(),
                updateUserRequest.phoneNumber()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("User updated successfully"));
    }

    @PatchMapping("/password")
    public ResponseEntity<MessageResponse> updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        this.updateUserPasswordUseCase.execute(UpdateUserPasswordUseCaseInput.with(
                updateUserPasswordRequest.currentPassword(), updateUserPasswordRequest.password()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("User password updated successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput = this.getAuthenticatedUserUseCase.execute();

        if (getAuthenticatedUserUseCaseOutput.authenticatedUser().getPersonType() == PersonType.NATURAL_PERSON) {
            return ResponseEntity.ok().body(GetAuthenticatedUserNaturalPersonResponse.from(getAuthenticatedUserUseCaseOutput));
        } else {
            return ResponseEntity.ok().body(GetAuthenticatedUserLegalPersonResponse.from(getAuthenticatedUserUseCaseOutput));
        }
    }
}