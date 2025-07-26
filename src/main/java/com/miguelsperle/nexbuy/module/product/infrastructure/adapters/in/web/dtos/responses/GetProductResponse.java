package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.complements.DimensionComplementResponse;

import java.math.BigDecimal;

public record GetProductResponse(
        String id,
        String name,
        String description,
        String categoryId,
        BigDecimal price,
        String sku,
        String brandId,
        String colorId,
        ProductStatus status,
        Integer weight,
        DimensionComplementResponse dimension
) {
    public static GetProductResponse from(GetProductUseCaseOutput getProductUseCaseOutput) {
        return new GetProductResponse(
                getProductUseCaseOutput.product().getId(),
                getProductUseCaseOutput.product().getName(),
                getProductUseCaseOutput.product().getDescription(),
                getProductUseCaseOutput.product().getCategoryId(),
                getProductUseCaseOutput.product().getPrice(),
                getProductUseCaseOutput.product().getSku(),
                getProductUseCaseOutput.product().getBrandId(),
                getProductUseCaseOutput.product().getColorId(),
                getProductUseCaseOutput.product().getProductStatus(),
                getProductUseCaseOutput.product().getWeight(),
                DimensionComplementResponse.from(
                        getProductUseCaseOutput.product().getHeight(),
                        getProductUseCaseOutput.product().getWidth(),
                        getProductUseCaseOutput.product().getLength()
                )
        );
    }
}
