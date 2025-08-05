package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.DeleteBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.RegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.UpdateBrandUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.RegisterBrandRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.UpdateBrandRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/brands")
@RequiredArgsConstructor
public class BrandAdminController {
    private final RegisterBrandUseCase registerBrandUseCase;
    private final UpdateBrandUseCase updateBrandUseCase;
    private final DeleteBrandUseCase deleteBrandUseCase;

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
}
