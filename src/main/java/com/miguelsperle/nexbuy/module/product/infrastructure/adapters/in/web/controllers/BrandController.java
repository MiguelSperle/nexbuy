package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetBrandResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetBrandsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final GetBrandsUseCase getBrandsUseCase;
    private final GetBrandUseCase getBrandUseCase;

    @GetMapping
    public ResponseEntity<List<GetBrandsResponse>> getBrands() {
        final GetBrandsUseCaseOutput getBrandsUseCaseOutput = this.getBrandsUseCase.execute();

        return ResponseEntity.ok().body(GetBrandsResponse.from(getBrandsUseCaseOutput));
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<GetBrandResponse> getBrand(@PathVariable String brandId) {
        final GetBrandUseCaseOutput getBrandUseCaseOutput = this.getBrandUseCase.execute(GetBrandUseCaseInput.with(brandId));

        return ResponseEntity.ok().body(GetBrandResponse.from(getBrandUseCaseOutput));
    }
}
