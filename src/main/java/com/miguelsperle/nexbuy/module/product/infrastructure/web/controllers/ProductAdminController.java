package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateProductStatusUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.complements.DimensionComplementInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateProductStatusUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateProductUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterProductRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateProductRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateProductStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {
    private final IRegisterProductUseCase registerProductUseCase;
    private final IUpdateProductUseCase updateProductUseCase;
    private final IUpdateProductStatusUseCase updateProductStatusUseCase;
    private final IDeleteProductUseCase deleteProductUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerProduct(@RequestBody @Valid RegisterProductRequest registerProductRequest) {
        final DimensionComplementInput dimensionComplementInput = new DimensionComplementInput(
                registerProductRequest.dimensionComplement().height(),
                registerProductRequest.dimensionComplement().width(),
                registerProductRequest.dimensionComplement().length()
        );

        this.registerProductUseCase.execute(new RegisterProductUseCaseInput(
                registerProductRequest.name(),
                registerProductRequest.description(),
                registerProductRequest.categoryId(),
                registerProductRequest.price(),
                registerProductRequest.brandId(),
                registerProductRequest.colorId(),
                registerProductRequest.weight(),
                dimensionComplementInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Product registered successfully"));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<MessageResponse> updateProduct(
            @PathVariable String productId,
            @RequestBody @Valid UpdateProductRequest updateProductRequest
    ) {
        final DimensionComplementInput dimensionComplementInput = new DimensionComplementInput(
                updateProductRequest.dimensionComplement().height(),
                updateProductRequest.dimensionComplement().width(),
                updateProductRequest.dimensionComplement().length()
        );

        this.updateProductUseCase.execute(new UpdateProductUseCaseInput(
                productId,
                updateProductRequest.name(),
                updateProductRequest.description(),
                updateProductRequest.categoryId(),
                updateProductRequest.price(),
                updateProductRequest.brandId(),
                updateProductRequest.colorId(),
                updateProductRequest.weight(),
                dimensionComplementInput
        ));

        return ResponseEntity.ok().body(new MessageResponse("Product updated successfully"));
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<MessageResponse> updateProductStatus(
            @PathVariable String productId,
            @RequestBody @Valid UpdateProductStatusRequest updateProductStatusRequest
    ) {
        this.updateProductStatusUseCase.execute(new UpdateProductStatusUseCaseInput(
                productId,
                updateProductStatusRequest.productStatus()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Product status updated successfully"));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable String productId) {
        this.deleteProductUseCase.execute(new DeleteProductUseCaseInput(productId));

        return ResponseEntity.ok().body(new MessageResponse("Product deleted successfully"));
    }
}
