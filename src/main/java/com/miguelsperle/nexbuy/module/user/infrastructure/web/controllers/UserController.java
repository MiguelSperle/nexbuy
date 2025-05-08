package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.*;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.LegalPersonInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.NaturalPersonInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IAuthorizationUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResendUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserToVerifiedUseCase;
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
    private final IAuthorizationUseCase authorizationUseCase;
    private final IResendUserVerificationCodeUseCase resendUserVerificationCodeUseCase;
    private final IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;

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

    @PostMapping("/authorization")
    public ResponseEntity<Object> authorization(@RequestBody @Valid AuthorizationRequest authorizationRequest) {
        final AuthorizationUseCaseOutput authorizationUseCaseOutput = this.authorizationUseCase.execute(new AuthorizationUseCaseInput(
                authorizationRequest.getEmail(), authorizationRequest.getPassword()
        ));

        return ResponseEntity.ok().body(new AuthorizationResponse(
                authorizationUseCaseOutput.getAccessToken(), authorizationUseCaseOutput.getRefreshToken(),
                HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value())
        );
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
}
