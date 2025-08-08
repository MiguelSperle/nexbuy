package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetProductsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetProductResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final GetProductsUseCase getProductsUseCase;
    private final GetProductUseCase getProductUseCase;

    @GetMapping
    public ResponseEntity<Pagination<GetProductsResponse>> getProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam Map<String, String> filters
    ) {
        final GetProductsUseCaseOutput getProductsUseCaseOutput = this.getProductsUseCase.execute(GetProductsUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction, filters)
        ));

        return ResponseEntity.ok().body(GetProductsResponse.from(getProductsUseCaseOutput));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable String productId) {
        final GetProductUseCaseOutput getProductUseCaseOutput = this.getProductUseCase.execute(GetProductUseCaseInput.with(productId));

        return ResponseEntity.ok().body(GetProductResponse.from(getProductUseCaseOutput));
    }
}
