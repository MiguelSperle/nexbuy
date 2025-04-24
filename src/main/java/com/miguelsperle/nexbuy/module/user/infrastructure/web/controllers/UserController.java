package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.JuridicalUserInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.PhysicalUserInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.CreateUserRequest;
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

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new MessageResponse("User created successfully", HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value())
        );
    }
}
