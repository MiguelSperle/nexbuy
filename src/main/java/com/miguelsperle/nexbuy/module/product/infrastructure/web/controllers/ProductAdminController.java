package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.complements.DimensionComplementInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {
    private final IRegisterProductUseCase registerProductUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerProduct(@RequestBody @Valid RegisterProductRequest registerProductRequest) {
        final DimensionComplementInput dimensionComplementInput = new DimensionComplementInput(
                registerProductRequest.dimensionComplement().height(),
                registerProductRequest.dimensionComplement().width(),
                registerProductRequest.dimensionComplement().length()
        );

        this.registerProductUseCase.execute(new RegisterProductUseCaseInput(
                registerProductRequest.name(),
                registerProductRequest.description(),
                registerProductRequest.categoryId(),
                registerProductRequest.price(),
                registerProductRequest.brandId(),
                registerProductRequest.colorId(),
                registerProductRequest.weight(),
                dimensionComplementInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Product registered successfully"));
    }
}
