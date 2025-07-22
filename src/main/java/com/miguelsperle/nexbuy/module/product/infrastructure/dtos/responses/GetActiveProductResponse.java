package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements.DimensionComplementResponse;

import java.math.BigDecimal;

public record GetActiveProductResponse(
        String id,
        String name,
        String description,
        String categoryId,
        BigDecimal price,
        String sku,
        String brandId,
        String colorId,
        Integer weight,
        DimensionComplementResponse dimension
) {
    public static GetActiveProductResponse from(GetActiveProductUseCaseOutput getActiveProductUseCaseOutput) {
        return new GetActiveProductResponse(
                getActiveProductUseCaseOutput.product().getId(),
                getActiveProductUseCaseOutput.product().getName(),
                getActiveProductUseCaseOutput.product().getDescription(),
                getActiveProductUseCaseOutput.product().getCategoryId(),
                getActiveProductUseCaseOutput.product().getPrice(),
                getActiveProductUseCaseOutput.product().getSku(),
                getActiveProductUseCaseOutput.product().getBrandId(),
                getActiveProductUseCaseOutput.product().getColorId(),
                getActiveProductUseCaseOutput.product().getWeight(),
                DimensionComplementResponse.from(
                        getActiveProductUseCaseOutput.product().getHeight(),
                        getActiveProductUseCaseOutput.product().getWidth(),
                        getActiveProductUseCaseOutput.product().getLength()
                )
        );
    }
}
