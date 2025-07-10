package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetBrandsResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetCategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IGetBrandsUseCase getBrandsUseCase;
    private final IGetCategoriesUseCase getCategoriesUseCase;

    @GetMapping("/brands")
    public ResponseEntity<List<GetBrandsResponse>> getBrands() {
        final GetBrandsUseCaseOutput getBrandsUseCaseOutput = this.getBrandsUseCase.execute();

        return ResponseEntity.ok().body(GetBrandsResponse.fromOutput(getBrandsUseCaseOutput));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<GetCategoriesResponse>> getCategories() {
        final GetCategoriesUseCaseOutput getCategoriesUseCaseOutput = this.getCategoriesUseCase.execute();

        return ResponseEntity.ok().body(GetCategoriesResponse.fromOutput(getCategoriesUseCaseOutput));
    }
}
