package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Product {
    private final String id;
    private final String name;
    private final String description;
    private final ProductCategory productCategory;
    private final BigDecimal price;
    private final String sku;
    private final ProductBrand productBrand;
    private final ProductModel productModel;
    private final ProductColor productColor;
    private final Boolean isActive;
    private final LocalDateTime createdAt;

    private Product(
            String id,
            String name,
            String description,
            ProductCategory productCategory,
            BigDecimal price,
            String sku,
            ProductBrand productBrand,
            ProductModel productModel,
            ProductColor productColor,
            Boolean isActive,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productCategory = productCategory;
        this.price = price;
        this.sku = sku;
        this.productBrand = productBrand;
        this.productModel = productModel;
        this.productColor = productColor;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public static Product newProduct(
            String name,
            String description,
            ProductCategory productCategory,
            BigDecimal price,
            String sku,
            ProductBrand productBrand,
            ProductModel productModel,
            ProductColor productColor
    ) {
        return new Product(
                UUID.randomUUID().toString(),
                name,
                description,
                productCategory,
                price,
                sku,
                productBrand,
                productModel,
                productColor,
                true,
                LocalDateTime.now()
        );
    }

    public static Product with(
            String id,
            String name,
            String description,
            ProductCategory productCategory,
            BigDecimal price,
            String sku,
            ProductBrand productBrand,
            ProductModel productModel,
            ProductColor productColor,
            Boolean isActive,
            LocalDateTime createdAt
    ) {
        return new Product(
                id,
                name,
                description,
                productCategory,
                price,
                sku,
                productBrand,
                productModel,
                productColor,
                isActive,
                createdAt
        );
    }
}
