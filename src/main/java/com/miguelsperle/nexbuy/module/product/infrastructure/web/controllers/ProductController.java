package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterProductBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductBrandUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterProductBrandRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IRegisterProductBrandUseCase registerProductBrandUseCase;

    @PostMapping("/brand/register")
    public ResponseEntity<Object> registerProductBrand(@RequestBody @Valid RegisterProductBrandRequest registerProductBrandRequest) {
        this.registerProductBrandUseCase.execute(new RegisterProductBrandUseCaseInput(registerProductBrandRequest.getName()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Product brand registered successfully"));
    }
}
