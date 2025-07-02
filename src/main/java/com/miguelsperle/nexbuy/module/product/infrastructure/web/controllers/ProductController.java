package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterBrandRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterCategoryRequest;
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
    private final IDeleteBrandUseCase deleteBrandUseCase;
    private final IRegisterCategoryUseCase registerCategoryUseCase;
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

    @DeleteMapping("/brands/{brandId}")
    public ResponseEntity<Object> deleteBrand(@PathVariable String brandId) {
        this.deleteBrandUseCase.execute(new DeleteBrandUseCaseInput(brandId));

        return ResponseEntity.ok().body(new MessageResponse("Brand deleted successfully"));
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> registerCategory(@RequestBody @Valid RegisterCategoryRequest registerCategoryRequest) {
        this.registerCategoryUseCase.execute(new RegisterCategoryUseCaseInput(
                registerCategoryRequest.getName(),
                registerCategoryRequest.getDescription(),
                registerCategoryRequest.getParentCategoryId()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Category registered successfully"));
    }

    @GetMapping("/categories")
    public List<GetCategoriesResponse> getCategories() {
        final GetCategoriesUseCaseOutput getCategoriesUseCaseOutput = this.getCategoriesUseCase.execute();

        return GetCategoriesResponse.fromOutput(getCategoriesUseCaseOutput);
    }
}