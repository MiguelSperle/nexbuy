package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.*;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.complements.DimensionComplementInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterProductRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateProductRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateProductStatusRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetProductsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {
    private final IRegisterProductUseCase registerProductUseCase;
    private final IUpdateProductUseCase updateProductUseCase;
    private final IUpdateProductStatusUseCase updateProductStatusUseCase;
    private final IDeleteProductUseCase deleteProductUseCase;
    private final IGetProductsUseCase getProductsUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerProduct(@RequestBody @Valid RegisterProductRequest registerProductRequest) {
        this.registerProductUseCase.execute(RegisterProductUseCaseInput.with(
                registerProductRequest.name(),
                registerProductRequest.description(),
                registerProductRequest.categoryId(),
                registerProductRequest.price(),
                registerProductRequest.brandId(),
                registerProductRequest.colorId(),
                registerProductRequest.weight(),
                DimensionComplementInput.with(
                        registerProductRequest.dimension().height(),
                        registerProductRequest.dimension().width(),
                        registerProductRequest.dimension().length()
                )
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Product registered successfully"));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<MessageResponse> updateProduct(
            @PathVariable String productId,
            @RequestBody @Valid UpdateProductRequest updateProductRequest
    ) {
        this.updateProductUseCase.execute(UpdateProductUseCaseInput.with(
                productId,
                updateProductRequest.name(),
                updateProductRequest.description(),
                updateProductRequest.categoryId(),
                updateProductRequest.price(),
                updateProductRequest.brandId(),
                updateProductRequest.colorId(),
                updateProductRequest.weight(),
                DimensionComplementInput.with(
                        updateProductRequest.dimension().height(),
                        updateProductRequest.dimension().width(),
                        updateProductRequest.dimension().length()
                )
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Product updated successfully"));
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<MessageResponse> updateProductStatus(
            @PathVariable String productId,
            @RequestBody @Valid UpdateProductStatusRequest updateProductStatusRequest
    ) {
        this.updateProductStatusUseCase.execute(UpdateProductStatusUseCaseInput.with(
                productId,
                updateProductStatusRequest.productStatus()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Product status updated successfully"));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable String productId) {
        this.deleteProductUseCase.execute(DeleteProductUseCaseInput.with(productId));

        return ResponseEntity.ok().body(MessageResponse.from("Product deleted successfully"));
    }

    @GetMapping
    public ResponseEntity<Pagination<GetProductsResponse>> getProducts(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam Map<String, String> filters
    ) {
        final GetProductsUseCaseOutput getProductsUseCaseOutput = this.getProductsUseCase.execute(GetProductsUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction, filters)
        ));

        return ResponseEntity.ok().body(GetProductsResponse.from(getProductsUseCaseOutput));
    }
}
