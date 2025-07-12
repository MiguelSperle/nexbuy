package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.AuthenticateUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IAuthenticateUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.AuthenticateRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.CreateUserRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.RefreshTokenRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.AuthenticateResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.RefreshTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ICreateUserUseCase createUserUseCase;
    private final IAuthenticateUseCase authenticateUseCase;
    private final IRefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        PersonComplementInput personComplementInput = null;

        final PersonType convertedToPersonType = PersonType.valueOf(createUserRequest.personType());

        if (convertedToPersonType == PersonType.NATURAL_PERSON && createUserRequest.naturalPersonComplement() != null) {
            personComplementInput = new PersonComplementInput(
                    createUserRequest.naturalPersonComplement().cpf(),
                    createUserRequest.naturalPersonComplement().generalRegister(),
                    null, null, null, null
            );
        } else if (convertedToPersonType == PersonType.LEGAL_PERSON && createUserRequest.legalPersonComplement() != null) {
            personComplementInput = new PersonComplementInput(
                    null, null,
                    createUserRequest.legalPersonComplement().cnpj(),
                    createUserRequest.legalPersonComplement().fantasyName(),
                    createUserRequest.legalPersonComplement().legalName(),
                    createUserRequest.legalPersonComplement().stateRegistration()
            );
        }

        this.createUserUseCase.execute(new CreateUserUseCaseInput(
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.email(),
                createUserRequest.password(),
                createUserRequest.phoneNumber(),
                convertedToPersonType,
                personComplementInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody @Valid AuthenticateRequest authenticateRequest) {
        final AuthenticateUseCaseOutput authenticateUseCaseOutput = this.authenticateUseCase.execute(new AuthenticateUseCaseInput(
                authenticateRequest.email(), authenticateRequest.password()
        ));

        return ResponseEntity.ok().body(AuthenticateResponse.fromOutput(authenticateUseCaseOutput));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        final RefreshTokenUseCaseOutput refreshTokenUseCaseOutput = this.refreshTokenUseCase.execute(new RefreshTokenUseCaseInput(
                refreshTokenRequest.refreshToken()
        ));

        return ResponseEntity.ok().body(RefreshTokenResponse.fromOutput(refreshTokenUseCaseOutput));
    }
}
