package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateBrandUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterBrandRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateBrandRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetBrandResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/brands")
@RequiredArgsConstructor
public class BrandAdminController {
    private final IRegisterBrandUseCase registerBrandUseCase;
    private final IUpdateBrandUseCase updateBrandUseCase;
    private final IDeleteBrandUseCase deleteBrandUseCase;
    private final IGetBrandUseCase getBrandUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerBrand(@RequestBody @Valid RegisterBrandRequest registerBrandRequest) {
        this.registerBrandUseCase.execute(RegisterBrandUseCaseInput.with(registerBrandRequest.name()));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Brand registered successfully"));
    }

    @PatchMapping("/{brandId}")
    public ResponseEntity<MessageResponse> updateBrand(
            @PathVariable String brandId,
            @RequestBody @Valid UpdateBrandRequest updateBrandRequest
    ) {
        this.updateBrandUseCase.execute(UpdateBrandUseCaseInput.with(
                brandId,
                updateBrandRequest.name()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Brand updated successfully"));
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<MessageResponse> deleteBrand(@PathVariable String brandId) {
        this.deleteBrandUseCase.execute(DeleteBrandUseCaseInput.with(brandId));

        return ResponseEntity.ok().body(MessageResponse.from("Brand deleted successfully"));
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<GetBrandResponse> getBrand(@PathVariable String brandId) {
        final GetBrandUseCaseOutput getBrandUseCaseOutput = this.getBrandUseCase.execute(GetBrandUseCaseInput.with(brandId));

        return ResponseEntity.ok().body(GetBrandResponse.from(getBrandUseCaseOutput));
    }
}
