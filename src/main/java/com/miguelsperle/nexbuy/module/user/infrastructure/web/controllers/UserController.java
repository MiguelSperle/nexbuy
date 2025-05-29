package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.*;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.LegalPersonInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.NaturalPersonInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final ICreateUserUseCase createUserUseCase;
    private final IAuthenticateUseCase authenticateUseCase;
    private final IResendUserVerificationCodeUseCase resendUserVerificationCodeUseCase;
    private final IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;
    private final IRefreshTokenUseCase refreshTokenUseCase;
    private final ICreateUserPasswordResetCodeUseCase createUserPasswordResetCodeUseCase;
    private final IValidateUserPasswordResetCodeUseCase validateUserPasswordResetCodeUseCase;
    private final IResetUserPasswordUseCase resetUserPasswordUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        final NaturalPersonInput naturalPersonInput = createUserRequest.getNaturalPersonComplement() != null ?
                new NaturalPersonInput(
                        createUserRequest.getNaturalPersonComplement().getCpf(),
                        createUserRequest.getNaturalPersonComplement().getGeneralRegister()
                ) : null;

        final LegalPersonInput legalPersonInput = createUserRequest.getLegalPersonComplement() != null ?
                new LegalPersonInput(
                        createUserRequest.getLegalPersonComplement().getCnpj(),
                        createUserRequest.getLegalPersonComplement().getFantasyName(),
                        createUserRequest.getLegalPersonComplement().getLegalName(),
                        createUserRequest.getLegalPersonComplement().getStateRegistration()
                ) : null;

        this.createUserUseCase.execute(new CreateUserUseCaseInput(
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword(),
                createUserRequest.getPhoneNumber(),
                createUserRequest.getPersonType(),
                naturalPersonInput,
                legalPersonInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(
                "User created successfully", HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value())
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid AuthenticateRequest authenticateRequest) {
        final AuthenticateUseCaseOutput authenticateUseCaseOutput = this.authenticateUseCase.execute(new AuthenticateUseCaseInput(
                authenticateRequest.getEmail(), authenticateRequest.getPassword()
        ));

        return ResponseEntity.ok().body(new AuthenticateResponse(
                authenticateUseCaseOutput.getAccessToken(), authenticateUseCaseOutput.getRefreshToken(),
                HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()
        ));
    }

    @PostMapping("/verification-code/resend")
    public ResponseEntity<Object> resendUserVerificationCode(@RequestBody @Valid ResendUserVerificationCodeRequest resendUserVerificationCodeRequest) {
        this.resendUserVerificationCodeUseCase.execute(new ResendUserVerificationCodeUseCaseInput(
                resendUserVerificationCodeRequest.getEmail()
        ));

        return ResponseEntity.ok().body(new MessageResponse(
                "User verification code sent successfully", HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value())
        );
    }

    @PatchMapping("/confirm-verification")
    public ResponseEntity<Object> updateUserToVerified(@RequestBody @Valid UpdateUserToVerifiedRequest updateUserToVerifiedRequest) {
        this.updateUserToVerifiedUseCase.execute(new UpdateUserToVerifiedUseCaseInput(
                updateUserToVerifiedRequest.getCode()
        ));

        return ResponseEntity.ok().body(new MessageResponse(
                "User verified successfully", HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()
        ));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        final RefreshTokenUseCaseOutput refreshTokenUseCaseOutput = this.refreshTokenUseCase.execute(new RefreshTokenUseCaseInput(
                refreshTokenRequest.getRefreshToken()
        ));

        return ResponseEntity.ok().body(new RefreshTokenResponse(
                refreshTokenUseCaseOutput.getAccessToken(), HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()
        ));
    }

    @PostMapping("/reset-password/send-recovery-email")
    public ResponseEntity<Object> createUserPasswordResetCode(@RequestBody @Valid CreateUserPasswordResetCodeRequest createUserPasswordResetCodeRequest) {
        this.createUserPasswordResetCodeUseCase.execute(new CreateUserPasswordResetCodeUseCaseInput(
                createUserPasswordResetCodeRequest.getEmail()
        ));

        return ResponseEntity.ok().body(new MessageResponse(
                "Password reset code sent successfully", HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()
        ));
    }

    @PostMapping("/reset-password/validate-code")
    public ResponseEntity<Object> validateUserPasswordResetCode(@RequestBody @Valid ValidateUserPasswordResetCodeRequest validateUserPasswordResetCodeRequest) {
        final ValidateUserPasswordResetCodeUseCaseOutput validateUserPasswordResetCodeUseCaseOutput = this.validateUserPasswordResetCodeUseCase.execute(new ValidateUserPasswordResetCodeUseCaseInput(
                validateUserPasswordResetCodeRequest.getCode()
        ));

        return ResponseEntity.ok().body(new ValidateUserPasswordResetCodeResponse(
                validateUserPasswordResetCodeUseCaseOutput.isCodeIsValid(), HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()
        ));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Object> resetUserPassword(@RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        this.resetUserPasswordUseCase.execute(new ResetUserPasswordUseCaseInput(
                resetUserPasswordRequest.getCode(), resetUserPasswordRequest.getPassword()
        ));

        return ResponseEntity.ok().body(new MessageResponse(
                "Password reset successfully", HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()
        ));
    }
}
