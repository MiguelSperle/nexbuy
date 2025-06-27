package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterProductBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterProductCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetProductBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetProductBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterProductBrandRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterProductCategoryRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetProductBrandsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IRegisterProductBrandUseCase registerProductBrandUseCase;
    private final IGetProductBrandsUseCase getProductBrandsUseCase;
    private final IRegisterProductCategoryUseCase registerProductCategoryUseCase;

    @PostMapping("/brand/register")
    public ResponseEntity<Object> registerProductBrand(@RequestBody @Valid RegisterProductBrandRequest registerProductBrandRequest) {
        this.registerProductBrandUseCase.execute(new RegisterProductBrandUseCaseInput(registerProductBrandRequest.getName()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Product brand registered successfully"));
    }

    @GetMapping("/brands")
    public List<GetProductBrandsResponse> getProductBrands() {
        final GetProductBrandsUseCaseOutput getProductBrandsUseCaseOutput = this.getProductBrandsUseCase.execute();

        return GetProductBrandsResponse.fromOutput(getProductBrandsUseCaseOutput);
    }

    @PostMapping("/category/register")
    public ResponseEntity<Object> registerProductCategory(@RequestBody @Valid RegisterProductCategoryRequest registerProductCategoryRequest) {
        this.registerProductCategoryUseCase.execute(new RegisterProductCategoryUseCaseInput(registerProductCategoryRequest.getName()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Product category registered successfully"));
    }
}
