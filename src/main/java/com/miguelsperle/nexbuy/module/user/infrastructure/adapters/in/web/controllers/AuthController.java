package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.AuthenticateUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.AuthenticateUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.RefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.AuthenticateRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.CreateUserRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.RefreshTokenRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.AuthenticateResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.RefreshTokenResponse;
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
    private final CreateUserUseCase createUserUseCase;
    private final AuthenticateUseCase authenticateUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        PersonComplementInput personComplementInput = null;

        final PersonType convertedToPersonType = PersonType.valueOf(createUserRequest.personType());

        if (convertedToPersonType == PersonType.NATURAL_PERSON && createUserRequest.naturalPerson() != null) {
            personComplementInput = PersonComplementInput.with(
                    createUserRequest.naturalPerson().cpf(),
                    createUserRequest.naturalPerson().generalRegister(),
                    null, null, null, null
            );
        } else if (convertedToPersonType == PersonType.LEGAL_PERSON && createUserRequest.legalPerson() != null) {
            personComplementInput = PersonComplementInput.with(
                    null, null,
                    createUserRequest.legalPerson().cnpj(),
                    createUserRequest.legalPerson().fantasyName(),
                    createUserRequest.legalPerson().legalName(),
                    createUserRequest.legalPerson().stateRegistration()
            );
        }

        this.createUserUseCase.execute(CreateUserUseCaseInput.with(
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.email(),
                createUserRequest.password(),
                createUserRequest.phoneNumber(),
                convertedToPersonType,
                personComplementInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("User created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody @Valid AuthenticateRequest authenticateRequest) {
        final AuthenticateUseCaseOutput authenticateUseCaseOutput = this.authenticateUseCase.execute(AuthenticateUseCaseInput.with(
                authenticateRequest.email(), authenticateRequest.password()
        ));

        return ResponseEntity.ok().body(AuthenticateResponse.from(authenticateUseCaseOutput));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        final RefreshTokenUseCaseOutput refreshTokenUseCaseOutput = this.refreshTokenUseCase.execute(RefreshTokenUseCaseInput.with(
                refreshTokenRequest.refreshToken()
        ));

        return ResponseEntity.ok().body(RefreshTokenResponse.from(refreshTokenUseCaseOutput));
    }
}
