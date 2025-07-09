package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetBrandResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetBrandsResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetCategoriesResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IGetBrandsUseCase getBrandsUseCase;
    private final IGetBrandUseCase getBrandUseCase;
    private final IGetCategoriesUseCase getCategoriesUseCase;
    private final IGetCategoryUseCase getCategoryUseCase;

    @GetMapping("/brands")
    public ResponseEntity<List<GetBrandsResponse>> getBrands() {
        final GetBrandsUseCaseOutput getBrandsUseCaseOutput = this.getBrandsUseCase.execute();

        return ResponseEntity.ok().body(GetBrandsResponse.fromOutput(getBrandsUseCaseOutput));
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<Object> getBrand(@PathVariable String brandId) {
        final GetBrandUseCaseOutput getBrandUseCaseOutput = this.getBrandUseCase.execute(new GetBrandUseCaseInput(brandId));

        return ResponseEntity.ok().body(GetBrandResponse.fromOutput(getBrandUseCaseOutput));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<GetCategoriesResponse>> getCategories() {
        final GetCategoriesUseCaseOutput getCategoriesUseCaseOutput = this.getCategoriesUseCase.execute();

        return ResponseEntity.ok().body(GetCategoriesResponse.fromOutput(getCategoriesUseCaseOutput));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Object> getCategory(@PathVariable String categoryId) {
        final GetCategoryUseCaseOutput getCategoryUseCaseOutput = this.getCategoryUseCase.execute(new GetCategoryUseCaseInput(categoryId));

        return ResponseEntity.ok().body(GetCategoryResponse.fromOutput(getCategoryUseCaseOutput));
    }
}
