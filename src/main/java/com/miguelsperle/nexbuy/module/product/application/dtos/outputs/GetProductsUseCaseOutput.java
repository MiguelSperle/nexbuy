package com.miguelsperle.nexbuy.module.product.application.dtos.outputs;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

import java.util.Map;

public record GetProductsUseCaseOutput(
        Pagination<Product> paginatedProducts,
        Map<String, Category> categoriesMap,
        Map<String, Brand> brandsMap,
        Map<String, Color> colorsMap
) {
    public static GetProductsUseCaseOutput from(
            Pagination<Product> paginatedProducts,
            Map<String, Category> categoriesMap,
            Map<String, Brand> brandsMap,
            Map<String, Color> colorsMap
    ) {
        return new GetProductsUseCaseOutput(
                paginatedProducts,
                categoriesMap,
                brandsMap,
                colorsMap
        );
    }
}
