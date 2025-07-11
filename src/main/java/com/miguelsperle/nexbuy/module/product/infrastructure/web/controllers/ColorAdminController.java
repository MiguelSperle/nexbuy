package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterColorRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/colors")
@RequiredArgsConstructor
public class ColorAdminController {
    private final IRegisterColorUseCase registerColorUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerColor(@RequestBody @Valid RegisterColorRequest registerColorRequest) {
        this.registerColorUseCase.execute(new RegisterColorUseCaseInput(registerColorRequest.name()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Color registered successfully"));
    }
}
