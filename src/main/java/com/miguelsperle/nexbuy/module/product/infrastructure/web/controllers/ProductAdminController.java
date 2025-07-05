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
    private final IUpdateBrandNameUseCase updateBrandNameUseCase;
    private final IDeleteBrandUseCase deleteBrandUseCase;
    private final IRegisterRootCategoryUseCase registerRootCategoryUseCase;
    private final IRegisterSubCategoryUseCase registerSubCategoryUseCase;
    private final IUpdateCategoryUseCase updateCategoryUseCase;

    @PostMapping("/brands")
    public ResponseEntity<Object> registerBrand(@RequestBody @Valid RegisterBrandRequest registerBrandRequest) {
        this.registerBrandUseCase.execute(new RegisterBrandUseCaseInput(registerBrandRequest.getName()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Brand registered successfully"));
    }

    @PatchMapping("/brands/{brandId}")
    public ResponseEntity<Object> updateBrandName(
            @PathVariable String brandId,
            @RequestBody @Valid UpdateBrandNameRequest updateBrandNameRequest
    ) {
        this.updateBrandNameUseCase.execute(new UpdateBrandNameUseCaseInput(
                brandId,
                updateBrandNameRequest.getName()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Brand name updated successfully"));
    }

    @DeleteMapping("/brands/{brandId}")
    public ResponseEntity<Object> deleteBrand(@PathVariable String brandId) {
        this.deleteBrandUseCase.execute(new DeleteBrandUseCaseInput(brandId));

        return ResponseEntity.ok().body(new MessageResponse("Brand deleted successfully"));
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> registerRootCategory(@RequestBody @Valid RegisterRootCategoryRequest registerRootCategoryRequest) {
        this.registerRootCategoryUseCase.execute(new RegisterRootCategoryUseCaseInput(
                registerRootCategoryRequest.getName(),
                registerRootCategoryRequest.getDescription()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Root category registered successfully"));
    }

    @PostMapping("/categories/{categoryId}/sub")
    public ResponseEntity<Object> registerSubCategoryOfRoot(
            @PathVariable String categoryId,
            @RequestBody @Valid RegisterSubCategoryRequest registerSubCategoryRequest
    ) {
        this.registerSubCategoryUseCase.execute(new RegisterSubCategoryUseCaseInput(
                categoryId,
                registerSubCategoryRequest.getName(),
                registerSubCategoryRequest.getDescription()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Sub category registered successfully"));
    }

    @PostMapping("/categories/sub/{categoryId}")
    public ResponseEntity<Object> registerSubCategoryOfSub(
            @PathVariable String categoryId,
            @RequestBody @Valid RegisterSubCategoryRequest registerSubCategoryRequest
    ) {
        this.registerSubCategoryUseCase.execute(new RegisterSubCategoryUseCaseInput(
                categoryId,
                registerSubCategoryRequest.getName(),
                registerSubCategoryRequest.getDescription()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Sub category registered successfully"));
    }

    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<Object> updateRootCategory(
            @PathVariable String categoryId,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest
    ) {
        this.updateCategoryUseCase.execute(new UpdateCategoryUseCaseInput(
                categoryId,
                updateCategoryRequest.getName(),
                updateCategoryRequest.getDescription()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Root category updated successfully"));
    }

    @PatchMapping("/categories/sub/{categoryId}")
    public ResponseEntity<Object> updateSubCategory(
            @PathVariable String categoryId,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest
    ) {
        this.updateCategoryUseCase.execute(new UpdateCategoryUseCaseInput(
                categoryId,
                updateCategoryRequest.getName(),
                updateCategoryRequest.getDescription()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Sub category updated successfully"));
    }
}