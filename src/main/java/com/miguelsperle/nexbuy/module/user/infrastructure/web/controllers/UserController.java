package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.*;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.*;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;
    private final IResetUserPasswordUseCase resetUserPasswordUseCase;
    private final IUpdateUserUseCase updateUserUseCase;
    private final IUpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final IGetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @PatchMapping("/verification")
    public ResponseEntity<MessageResponse> updateUserToVerified(@RequestBody @Valid UpdateUserToVerifiedRequest updateUserToVerifiedRequest) {
        this.updateUserToVerifiedUseCase.execute(new UpdateUserToVerifiedUseCaseInput(
                updateUserToVerifiedRequest.code()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User verified successfully"));
    }

    @PatchMapping("/password-reset")
    public ResponseEntity<MessageResponse> resetUserPassword(@RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        this.resetUserPasswordUseCase.execute(new ResetUserPasswordUseCaseInput(
                resetUserPasswordRequest.code(), resetUserPasswordRequest.password()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User password reset successfully"));
    }

    @PatchMapping("/information")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        this.updateUserUseCase.execute(new UpdateUserUseCaseInput(
                updateUserRequest.firstName(), updateUserRequest.lastName(),
                updateUserRequest.phoneNumber()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User updated successfully"));
    }

    @PatchMapping("/password")
    public ResponseEntity<MessageResponse> updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        this.updateUserPasswordUseCase.execute(new UpdateUserPasswordUseCaseInput(
                updateUserPasswordRequest.currentPassword(), updateUserPasswordRequest.password()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User password updated successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput = this.getAuthenticatedUserUseCase.execute();

        if (getAuthenticatedUserUseCaseOutput.authenticatedUser().getPersonType() == PersonType.NATURAL_PERSON) {
            return ResponseEntity.ok().body(GetAuthenticatedUserNaturalPersonResponse.fromOutput(getAuthenticatedUserUseCaseOutput));
        } else {
            return ResponseEntity.ok().body(GetAuthenticatedUserLegalPersonResponse.fromOutput(getAuthenticatedUserUseCaseOutput));
        }
    }
}