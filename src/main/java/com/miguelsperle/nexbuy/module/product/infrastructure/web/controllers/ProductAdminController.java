package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.*;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.*;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetBrandResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {
    private final IRegisterBrandUseCase registerBrandUseCase;
    private final IUpdateBrandUseCase updateBrandUseCase;
    private final IDeleteBrandUseCase deleteBrandUseCase;
    private final IGetBrandUseCase getBrandUseCase;
    private final IRegisterCategoryUseCase registerCategoryUseCase;
    private final IUpdateCategoryUseCase updateCategoryUseCase;
    private final IDeleteCategoryUseCase deleteCategoryUseCase;
    private final IGetCategoryUseCase getCategoryUseCase;

    @PostMapping("/brands")
    public ResponseEntity<Object> registerBrand(@RequestBody @Valid RegisterBrandRequest registerBrandRequest) {
        this.registerBrandUseCase.execute(new RegisterBrandUseCaseInput(
                registerBrandRequest.name()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Brand registered successfully"));
    }

    @PatchMapping("/brands/{brandId}")
    public ResponseEntity<Object> updateBrand(
            @PathVariable String brandId,
            @RequestBody @Valid UpdateBrandRequest updateBrandRequest
    ) {
        this.updateBrandUseCase.execute(new UpdateBrandUseCaseInput(
                brandId,
                updateBrandRequest.name()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Brand updated successfully"));
    }

    @DeleteMapping("/brands/{brandId}")
    public ResponseEntity<Object> deleteBrand(@PathVariable String brandId) {
        this.deleteBrandUseCase.execute(new DeleteBrandUseCaseInput(brandId));

        return ResponseEntity.ok().body(new MessageResponse("Brand deleted successfully"));
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<Object> getBrand(@PathVariable String brandId) {
        final GetBrandUseCaseOutput getBrandUseCaseOutput = this.getBrandUseCase.execute(new GetBrandUseCaseInput(brandId));

        return ResponseEntity.ok().body(GetBrandResponse.fromOutput(getBrandUseCaseOutput));
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> registerCategory(@RequestBody @Valid RegisterCategoryRequest registerCategoryRequest) {
        this.registerCategoryUseCase.execute(new RegisterCategoryUseCaseInput(
                registerCategoryRequest.name()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Category registered successfully"));
    }

    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable String categoryId,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest
    ) {
        this.updateCategoryUseCase.execute(new UpdateCategoryUseCaseInput(
                categoryId,
                updateCategoryRequest.name()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Category updated successfully"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String categoryId) {
        this.deleteCategoryUseCase.execute(new DeleteCategoryUseCaseInput(categoryId));

        return ResponseEntity.ok().body(new MessageResponse("Category deleted successfully"));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Object> getCategory(@PathVariable String categoryId) {
        final GetCategoryUseCaseOutput getCategoryUseCaseOutput = this.getCategoryUseCase.execute(new GetCategoryUseCaseInput(categoryId));

        return ResponseEntity.ok().body(GetCategoryResponse.fromOutput(getCategoryUseCaseOutput));
    }
}