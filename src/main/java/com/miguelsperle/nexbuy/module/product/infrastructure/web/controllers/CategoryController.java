package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoryUseCase;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final IGetCategoriesUseCase getCategoriesUseCase;
    private final IGetCategoryUseCase getCategoryUseCase;

    @GetMapping
    public ResponseEntity<List<GetCategoriesResponse>> getCategories() {
        final GetCategoriesUseCaseOutput getCategoriesUseCaseOutput = this.getCategoriesUseCase.execute();

        return ResponseEntity.ok().body(GetCategoriesResponse.from(getCategoriesUseCaseOutput));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<GetCategoryResponse> getCategory(@PathVariable String categoryId) {
        final GetCategoryUseCaseOutput getCategoryUseCaseOutput = this.getCategoryUseCase.execute(GetCategoryUseCaseInput.with(categoryId));

        return ResponseEntity.ok().body(GetCategoryResponse.from(getCategoryUseCaseOutput));
    }
}
