package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetBrandResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetBrandsResponse;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final GetBrandsUseCase getBrandsUseCase;
    private final GetBrandUseCase getBrandUseCase;

    @GetMapping
    public ResponseEntity<Pagination<GetBrandsResponse>> getBrands(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    ) {
        final GetBrandsUseCaseOutput getBrandsUseCaseOutput = this.getBrandsUseCase.execute(GetBrandsUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction)
        ));

        return ResponseEntity.ok().body(GetBrandsResponse.from(getBrandsUseCaseOutput));
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<GetBrandResponse> getBrand(@PathVariable String brandId) {
        final GetBrandUseCaseOutput getBrandUseCaseOutput = this.getBrandUseCase.execute(GetBrandUseCaseInput.with(brandId));

        return ResponseEntity.ok().body(GetBrandResponse.from(getBrandUseCaseOutput));
    }
}
