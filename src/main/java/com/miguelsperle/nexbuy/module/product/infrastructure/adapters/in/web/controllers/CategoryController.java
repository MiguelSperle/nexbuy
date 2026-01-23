package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.GetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.GetCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetCategoriesResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetCategoryResponse;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final GetCategoriesUseCase getCategoriesUseCase;
    private final GetCategoryUseCase getCategoryUseCase;

    @GetMapping
    public ResponseEntity<Pagination<GetCategoriesResponse>> getCategories(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    ) {
        final GetCategoriesUseCaseOutput getCategoriesUseCaseOutput = this.getCategoriesUseCase.execute(GetCategoriesUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction)
        ));

        return ResponseEntity.ok().body(GetCategoriesResponse.from(getCategoriesUseCaseOutput));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<GetCategoryResponse> getCategory(@PathVariable String categoryId) {
        final GetCategoryUseCaseOutput getCategoryUseCaseOutput = this.getCategoryUseCase.execute(GetCategoryUseCaseInput.with(categoryId));

        return ResponseEntity.ok().body(GetCategoryResponse.from(getCategoryUseCaseOutput));
    }
}
