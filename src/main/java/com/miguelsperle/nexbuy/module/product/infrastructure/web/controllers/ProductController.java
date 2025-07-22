package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetActiveProductsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetActiveProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetActiveProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetActiveProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetActiveProductsResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetActiveProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IGetActiveProductsUseCase getActiveProductsUseCase;
    private final IGetActiveProductUseCase getActiveProductUseCase;

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

    @GetMapping("/{productId}/active")
    public ResponseEntity<GetActiveProductResponse> getActiveProduct(@PathVariable String productId) {
        final GetActiveProductUseCaseOutput getActiveProductUseCaseOutput = this.getActiveProductUseCase.execute(GetActiveProductUseCaseInput.with(productId));

        return ResponseEntity.ok().body(GetActiveProductResponse.from(getActiveProductUseCaseOutput));
    }
}
