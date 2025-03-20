package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IMediator;
import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.core.infrastructure.web.baseController.AbstractBaseController;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserHandlerInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserHandlerInput;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.CreateJuridicalUserRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.CreatePhysicalUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends AbstractBaseController {
    private final IMediator mediator;

    @PostMapping("/physical/create")
    public ResponseEntity<Object> createPhysicalUser(
            @RequestBody @Valid CreatePhysicalUserRequest createPhysicalUserRequest, BindingResult bindingResult
    ) {
        this.validateRequestBody(bindingResult);

        this.mediator.send(new CreatePhysicalUserHandlerInput(createPhysicalUserRequest.getFirstName(), createPhysicalUserRequest.getLastName(),
                createPhysicalUserRequest.getEmail(), createPhysicalUserRequest.getPassword(), createPhysicalUserRequest.getPhoneNumber(),
                createPhysicalUserRequest.getCpf(), createPhysicalUserRequest.getGeneralRegister()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully", HttpStatus.CREATED.value()));
    }

    @PostMapping("/juridical/create")
    public ResponseEntity<Object> createJuridicalUser(
            @RequestBody @Valid CreateJuridicalUserRequest createJuridicalUserRequest, BindingResult bindingResult
    ) {
        this.validateRequestBody(bindingResult);

        this.mediator.send(new CreateJuridicalUserHandlerInput(createJuridicalUserRequest.getFirstName(), createJuridicalUserRequest.getLastName(),
                createJuridicalUserRequest.getEmail(), createJuridicalUserRequest.getPassword(), createJuridicalUserRequest.getPhoneNumber(),
                createJuridicalUserRequest.getCnpj(), createJuridicalUserRequest.getFantasyName(), createJuridicalUserRequest.getLegalName(),
                createJuridicalUserRequest.getStateRegistration()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully", HttpStatus.CREATED.value()));
    }
}
