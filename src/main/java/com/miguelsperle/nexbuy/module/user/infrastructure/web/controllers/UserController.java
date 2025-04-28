package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthorizationUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthorizationUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.ResendUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.JuridicalUserInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.PhysicalUserInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IAuthorizationUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResendUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.AuthorizationRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.AuthorizationResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.CreateUserRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.ResendUserVerificationCodeRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final ICreateUserUseCase createUserUseCase;
    private final IAuthorizationUseCase authorizationUseCase;
    private final IResendUserVerificationCodeUseCase resendUserVerificationCodeUseCase;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        final PhysicalUserInput physicalUserInput = createUserRequest.getPhysicalUserComplement() != null ?
                new PhysicalUserInput(
                        createUserRequest.getPhysicalUserComplement().getCpf(),
                        createUserRequest.getPhysicalUserComplement().getGeneralRegister()
                ) : null;

        final JuridicalUserInput juridicalUserInput = createUserRequest.getJuridicalUserComplement() != null ?
                new JuridicalUserInput(
                        createUserRequest.getJuridicalUserComplement().getCnpj(),
                        createUserRequest.getJuridicalUserComplement().getFantasyName(),
                        createUserRequest.getJuridicalUserComplement().getLegalName(),
                        createUserRequest.getJuridicalUserComplement().getStateRegistration()
                ) : null;

        this.createUserUseCase.execute(new CreateUserUseCaseInput(
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword(),
                createUserRequest.getPhoneNumber(),
                createUserRequest.getUserType(),
                physicalUserInput,
                juridicalUserInput
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
                authorizationUseCaseOutput.getJwtToken(), HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value())
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
}
