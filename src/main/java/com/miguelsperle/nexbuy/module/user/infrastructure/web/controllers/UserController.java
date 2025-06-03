package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.LegalPersonDataInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.NaturalPersonDataInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.*;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.AuthenticateUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.ValidateUserPasswordResetCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.*;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.AuthenticateResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.GetAuthenticatedUserResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.RefreshTokenResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.ValidateUserPasswordResetCodeResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.LegalPersonDataResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements.NaturalPersonDataResponse;
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
    private final IUpdateUserInformationUseCase updateUserInformationUseCase;
    private final IGetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        NaturalPersonDataInput naturalPersonDataInput = null;
        LegalPersonDataInput legalPersonDataInput = null;

        if (createUserRequest.getNaturalPersonData() != null) {
            naturalPersonDataInput = new NaturalPersonDataInput(
                    createUserRequest.getNaturalPersonData().getCpf(),
                    createUserRequest.getNaturalPersonData().getGeneralRegister()
            );
        } else if (createUserRequest.getLegalPersonData() != null) {
            legalPersonDataInput = new LegalPersonDataInput(
                    createUserRequest.getLegalPersonData().getCnpj(),
                    createUserRequest.getLegalPersonData().getFantasyName(),
                    createUserRequest.getLegalPersonData().getLegalName(),
                    createUserRequest.getLegalPersonData().getStateRegistration()
            );
        }

        this.createUserUseCase.execute(new CreateUserUseCaseInput(
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword(),
                createUserRequest.getPhoneNumber(),
                createUserRequest.getPersonType(),
                naturalPersonDataInput,
                legalPersonDataInput
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

    @PatchMapping("/update-information")
    public ResponseEntity<Object> updateUserInformation(@RequestBody @Valid UpdateUserInformationRequest updateUserInformationRequest) {
        this.updateUserInformationUseCase.execute(new UpdateUserInformationUseCaseInput(
                updateUserInformationRequest.getFirstName(), updateUserInformationRequest.getLastName(),
                updateUserInformationRequest.getPhoneNumber()
        ));

        return ResponseEntity.ok().body(new MessageResponse(
                "User information updated successfully", HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<GetAuthenticatedUserResponse> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput = this.getAuthenticatedUserUseCase.execute();

        NaturalPersonDataResponse naturalPersonDataResponse = null;
        LegalPersonDataResponse legalPersonDataResponse = null;

        if (getAuthenticatedUserUseCaseOutput.getNaturalPersonDataOutput() != null) {
            naturalPersonDataResponse = new NaturalPersonDataResponse(
                    getAuthenticatedUserUseCaseOutput.getNaturalPersonDataOutput().getCpf(),
                    getAuthenticatedUserUseCaseOutput.getNaturalPersonDataOutput().getGeneralRegister()
            );
        } else if (getAuthenticatedUserUseCaseOutput.getLegalPersonDataOutput() != null) {
            legalPersonDataResponse = new LegalPersonDataResponse(
                    getAuthenticatedUserUseCaseOutput.getLegalPersonDataOutput().getCnpj(),
                    getAuthenticatedUserUseCaseOutput.getLegalPersonDataOutput().getFantasyName(),
                    getAuthenticatedUserUseCaseOutput.getLegalPersonDataOutput().getLegalName(),
                    getAuthenticatedUserUseCaseOutput.getLegalPersonDataOutput().getStateRegistration()
            );
        }

        return ResponseEntity.ok().body(new GetAuthenticatedUserResponse(
                getAuthenticatedUserUseCaseOutput.getFirstName(),
                getAuthenticatedUserUseCaseOutput.getLastName(),
                getAuthenticatedUserUseCaseOutput.getEmail(),
                getAuthenticatedUserUseCaseOutput.getPhoneNumber(),
                getAuthenticatedUserUseCaseOutput.getAuthorizationRole(),
                getAuthenticatedUserUseCaseOutput.getPersonType(),
                naturalPersonDataResponse,
                legalPersonDataResponse,
                HttpStatus.OK.getReasonPhrase(),
                HttpStatus.OK.value()
        ));
    }
}
