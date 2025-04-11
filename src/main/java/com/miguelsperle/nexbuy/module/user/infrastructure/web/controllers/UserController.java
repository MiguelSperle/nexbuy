package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.core.infrastructure.web.baseController.AbstractBaseController;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.JuridicalUserInputComplement;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.PhysicalUserInputComplement;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.CreateUserRequest;
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
    private final ICreateUserUseCase createUserUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(
            @RequestBody @Valid CreateUserRequest createUserRequest, BindingResult bindingResult
    ) {
        this.validateRequestBody(bindingResult);

        final PhysicalUserInputComplement physicalUserInputComplement = createUserRequest.getPhysicalUserComplement() != null ?
                new PhysicalUserInputComplement(
                        createUserRequest.getPhysicalUserComplement().getCpf(),
                        createUserRequest.getPhysicalUserComplement().getGeneralRegister()
                ) : null;

        final JuridicalUserInputComplement juridicalUserInputComplement = createUserRequest.getJuridicalUserComplement() != null ?
                new JuridicalUserInputComplement(
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
                physicalUserInputComplement,
                juridicalUserInputComplement
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully", HttpStatus.CREATED.value()));
    }
}
