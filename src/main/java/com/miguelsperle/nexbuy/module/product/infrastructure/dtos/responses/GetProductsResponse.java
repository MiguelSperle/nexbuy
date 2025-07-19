package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
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
        final Pagination<Product> productsPaginated = getProductsUseCaseOutput.productsPaginated();

        return productsPaginated.map(productPaginated -> new GetProductsResponse(
                productPaginated.getId(),
                productPaginated.getName(),
                productPaginated.getDescription(),
                CategoryComplementResponse.from(productPaginated.getCategory().getName()),
                productPaginated.getPrice(),
                productPaginated.getSku(),
                BrandComplementResponse.from(productPaginated.getBrand().getName()),
                ColorComplementResponse.from(productPaginated.getColor().getName()),
                productPaginated.getProductStatus(),
                productPaginated.getWeight(),
                DimensionComplementResponse.from(productPaginated.getHeight(), productPaginated.getWidth(), productPaginated.getLength())
        ));
    }
}
