package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.*;
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
    private final IRegisterCategoryUseCase registerCategoryUseCase;
    private final IUpdateCategoryUseCase updateCategoryUseCase;
    private final IDeleteCategoryUseCase deleteCategoryUseCase;

    @PostMapping("/brands")
    public ResponseEntity<Object> registerBrand(@RequestBody @Valid RegisterBrandRequest registerBrandRequest) {
        this.registerBrandUseCase.execute(new RegisterBrandUseCaseInput(
                registerBrandRequest.name(),
                registerBrandRequest.description()
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
                updateBrandRequest.name(),
                updateBrandRequest.description()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Brand updated successfully"));
    }

    @DeleteMapping("/brands/{brandId}")
    public ResponseEntity<Object> deleteBrand(@PathVariable String brandId) {
        this.deleteBrandUseCase.execute(new DeleteBrandUseCaseInput(brandId));

        return ResponseEntity.ok().body(new MessageResponse("Brand deleted successfully"));
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> registerCategory(@RequestBody @Valid RegisterCategoryRequest registerCategoryRequest) {
        this.registerCategoryUseCase.execute(new RegisterCategoryUseCaseInput(
                registerCategoryRequest.name(),
                registerCategoryRequest.description()
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
                updateCategoryRequest.name(),
                updateCategoryRequest.description()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Category updated successfully"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String categoryId) {
        this.deleteCategoryUseCase.execute(new DeleteCategoryUseCaseInput(categoryId));

        return ResponseEntity.ok().body(new MessageResponse("Category deleted successfully"));
    }
}