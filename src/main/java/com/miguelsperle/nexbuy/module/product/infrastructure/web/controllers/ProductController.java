package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetActiveProductsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetActiveProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetActiveProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IGetActiveProductsUseCase getActiveProductsUseCase;

    @GetMapping("/active")
    public ResponseEntity<Pagination<GetActiveProductsResponse>> getActiveProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam Map<String, String> filters
    ) {
        final GetActiveProductsUseCaseOutput getActiveProductsUseCaseOutput = this.getActiveProductsUseCase.execute(GetActiveProductsUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction, filters)
        ));

        return ResponseEntity.ok().body(GetActiveProductsResponse.from(getActiveProductsUseCaseOutput));
    }
}
