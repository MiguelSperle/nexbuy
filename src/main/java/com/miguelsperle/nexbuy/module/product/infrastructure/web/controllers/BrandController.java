package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetBrandsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final IGetBrandsUseCase getBrandsUseCase;

    @GetMapping
    public ResponseEntity<List<GetBrandsResponse>> getBrands() {
        final GetBrandsUseCaseOutput getBrandsUseCaseOutput = this.getBrandsUseCase.execute();

        return ResponseEntity.ok().body(GetBrandsResponse.from(getBrandsUseCaseOutput));
    }
}
