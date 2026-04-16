package com.miguelsperle.nexbuy.module.product.infrastructure.rest.controllers.admin;

import com.miguelsperle.nexbuy.shared.infrastructure.rest.dtos.res.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.abstractions.usecases.DeleteProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.abstractions.usecases.RegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.abstractions.usecases.UpdateProductStatusUseCase;
import com.miguelsperle.nexbuy.module.product.application.abstractions.usecases.UpdateProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.complements.DimensionComplementInput;
import com.miguelsperle.nexbuy.module.product.infrastructure.rest.dtos.req.RegisterProductRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.rest.dtos.req.UpdateProductRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.rest.dtos.req.UpdateProductStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {
    private final RegisterProductUseCase registerProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final UpdateProductStatusUseCase updateProductStatusUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

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
}
