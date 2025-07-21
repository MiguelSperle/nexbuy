package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements.DimensionComplementResponse;

import java.math.BigDecimal;

public record GetProductsResponse(
        String id,
        String name,
        String description,
        String categoryId,
        BigDecimal price,
        String sku,
        String brandId,
        String colorId,
        ProductStatus productStatus,
        Integer weight,
        DimensionComplementResponse dimension
) {
    public static Pagination<GetProductsResponse> from(GetProductsUseCaseOutput getProductsUseCaseOutput) {
        return getProductsUseCaseOutput.paginatedProducts().map(paginatedProduct -> new GetProductsResponse(
                paginatedProduct.getId(),
                paginatedProduct.getName(),
                paginatedProduct.getDescription(),
                paginatedProduct.getCategoryId(),
                paginatedProduct.getPrice(),
                paginatedProduct.getSku(),
                paginatedProduct.getBrandId(),
                paginatedProduct.getColorId(),
                paginatedProduct.getProductStatus(),
                paginatedProduct.getWeight(),
                DimensionComplementResponse.from(paginatedProduct.getHeight(), paginatedProduct.getWidth(), paginatedProduct.getLength())
        ));
    }
}
