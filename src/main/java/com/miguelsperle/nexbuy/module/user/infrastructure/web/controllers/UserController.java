package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IMediator;
import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.core.infrastructure.web.baseController.AbstractBaseController;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserHandlerInput;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.CreateUserRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.complement.JuridicalUserComplement;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.complement.PhysicalUserComplement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends AbstractBaseController {
    private final IMediator mediator;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(
            @RequestBody @Valid CreateUserRequest createUserRequest, BindingResult bindingResult
    ) {
        this.validateRequestBody(bindingResult);

        this.mediator.send(new CreateUserHandlerInput(
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword(),
                createUserRequest.getPhoneNumber(),
                createUserRequest.getUserType(),
                Optional.ofNullable(createUserRequest.getPhysicalUserComplement()).map(PhysicalUserComplement::getCpf).orElse(null),
                Optional.ofNullable(createUserRequest.getPhysicalUserComplement()).map(PhysicalUserComplement::getGeneralRegister).orElse(null),
                Optional.ofNullable(createUserRequest.getJuridicalUserComplement()).map(JuridicalUserComplement::getCnpj).orElse(null),
                Optional.ofNullable(createUserRequest.getJuridicalUserComplement()).map(JuridicalUserComplement::getFantasyName).orElse(null),
                Optional.ofNullable(createUserRequest.getJuridicalUserComplement()).map(JuridicalUserComplement::getLegalName).orElse(null),
                Optional.ofNullable(createUserRequest.getJuridicalUserComplement()).map(JuridicalUserComplement::getStateRegistration).orElse(null)
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully", HttpStatus.CREATED.value()));
    }
}
