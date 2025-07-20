package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements.BrandComplementResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements.CategoryComplementResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements.ColorComplementResponse;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.complements.DimensionComplementResponse;

import java.math.BigDecimal;

public record GetProductsResponse(
        String id,
        String name,
        String description,
        CategoryComplementResponse category,
        BigDecimal price,
        String sku,
        BrandComplementResponse brand,
        ColorComplementResponse color,
        ProductStatus productStatus,
        Integer weight,
        DimensionComplementResponse dimension
) {
    public static Pagination<GetProductsResponse> from(GetProductsUseCaseOutput getProductsUseCaseOutput) {
        return getProductsUseCaseOutput.paginatedProducts().map(paginatedProduct -> new GetProductsResponse(
                paginatedProduct.getId(),
                paginatedProduct.getName(),
                paginatedProduct.getDescription(),
                CategoryComplementResponse.from(getProductsUseCaseOutput.categoriesMap().get(paginatedProduct.getCategoryId()).getName()),
                paginatedProduct.getPrice(),
                paginatedProduct.getSku(),
                BrandComplementResponse.from(getProductsUseCaseOutput.brandsMap().get(paginatedProduct.getBrandId()).getName()),
                ColorComplementResponse.from(getProductsUseCaseOutput.colorsMap().get(paginatedProduct.getColorId()).getName()),
                paginatedProduct.getProductStatus(),
                paginatedProduct.getWeight(),
                DimensionComplementResponse.from(paginatedProduct.getHeight(), paginatedProduct.getWidth(), paginatedProduct.getLength())
        ));
    }
}
