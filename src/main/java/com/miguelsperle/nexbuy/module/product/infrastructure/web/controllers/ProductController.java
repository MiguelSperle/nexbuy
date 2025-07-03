package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateBrandStatusUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterRootCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterSubCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterBrandRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterRootCategoryRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterSubCategoryRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateBrandStatusRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetBrandsResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetCategoriesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IRegisterBrandUseCase registerBrandUseCase;
    private final IGetBrandsUseCase getBrandsUseCase;
    private final IUpdateBrandStatusUseCase updateBrandStatusUseCase;
    private final IRegisterRootCategoryUseCase registerRootCategoryUseCase;
    private final IRegisterSubCategoryUseCase registerSubCategoryUseCase;
    private final IGetCategoriesUseCase getCategoriesUseCase;

    @PostMapping("/brands")
    public ResponseEntity<Object> registerBrand(@RequestBody @Valid RegisterBrandRequest registerBrandRequest) {
        this.registerBrandUseCase.execute(new RegisterBrandUseCaseInput(registerBrandRequest.getName()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Brand registered successfully"));
    }

    @GetMapping("/brands")
    public List<GetBrandsResponse> getBrands() {
        final GetBrandsUseCaseOutput getBrandsUseCaseOutput = this.getBrandsUseCase.execute();

        return GetBrandsResponse.fromOutput(getBrandsUseCaseOutput);
    }

    @PatchMapping("/brands/{brandId}")
    public ResponseEntity<Object> updateBrandStatus(
            @PathVariable String brandId,
            @RequestBody @Valid UpdateBrandStatusRequest updateBrandStatusRequest
    ) {
        this.updateBrandStatusUseCase.execute(new UpdateBrandStatusUseCaseInput(
                brandId,
                updateBrandStatusRequest.getBrandStatus()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Brand updated successfully"));
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> registerRootCategory(@RequestBody @Valid RegisterRootCategoryRequest registerRootCategoryRequest) {
        this.registerRootCategoryUseCase.execute(new RegisterRootCategoryUseCaseInput(
                registerRootCategoryRequest.getName(),
                registerRootCategoryRequest.getDescription()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Root category registered successfully"));
    }

    @PostMapping("/categories/sub")
    public ResponseEntity<Object> registerSubCategory(@RequestBody @Valid RegisterSubCategoryRequest registerSubCategoryRequest) {
        this.registerSubCategoryUseCase.execute(new RegisterSubCategoryUseCaseInput(
                registerSubCategoryRequest.getName(),
                registerSubCategoryRequest.getDescription(),
                registerSubCategoryRequest.getParentCategoryId()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Sub category registered successfully"));
    }

    @GetMapping("/categories")
    public List<GetCategoriesResponse> getCategories() {
        final GetCategoriesUseCaseOutput getCategoriesUseCaseOutput = this.getCategoriesUseCase.execute();

        return GetCategoriesResponse.fromOutput(getCategoriesUseCaseOutput);
    }
}