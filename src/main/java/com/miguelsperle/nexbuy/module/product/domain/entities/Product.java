package com.miguelsperle.nexbuy.module.product.domain.entities;

import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    private final String id;
    private final String name;
    private final String description;
    private final Category category;
    private final BigDecimal price;
    private final String sku;
    private final Brand brand;
    private final Color color;
    private final ProductStatus productStatus;
    private final Integer weight;
    private final Integer height;
    private final Integer width;
    private final Integer length;
    private final LocalDateTime createdAt;

    private Product(
            String id,
            String name,
            String description,
            Category category,
            BigDecimal price,
            String sku,
            Brand brand,
            Color color,
            ProductStatus productStatus,
            Integer weight,
            Integer height,
            Integer width,
            Integer length,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.sku = sku;
        this.brand = brand;
        this.color = color;
        this.productStatus = productStatus;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.createdAt = createdAt;
    }

    public static Product newProduct(
            String name,
            String description,
            Category category,
            BigDecimal price,
            String sku,
            Brand brand,
            Color color,
            Integer weight,
            Integer height,
            Integer width,
            Integer length
    ) {
        return new Product(
                UUID.randomUUID().toString(),
                name,
                description,
                category,
                price,
                sku,
                brand,
                color,
                ProductStatus.ACTIVE,
                weight,
                height,
                width,
                length,
                LocalDateTime.now()
        );
    }

    public static Product with(
            String id,
            String name,
            String description,
            Category category,
            BigDecimal price,
            String sku,
            Brand brand,
            Color color,
            ProductStatus productStatus,
            Integer weight,
            Integer height,
            Integer width,
            Integer length,
            LocalDateTime createdAt
    ) {
        return new Product(
                id,
                name,
                description,
                category,
                price,
                sku,
                brand,
                color,
                productStatus,
                weight,
                height,
                width,
                length,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getSku() {
        return this.sku;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public Color getColor() {
        return this.color;
    }

    public ProductStatus getProductStatus() {
        return this.productStatus;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Integer getLength() {
        return this.length;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", category=" + this.category +
                ", price=" + this.price +
                ", sku='" + this.sku + '\'' +
                ", brand=" + this.brand +
                ", color=" + this.color +
                ", productStatus=" + this.productStatus +
                ", weight=" + this.weight +
                ", height=" + this.height +
                ", width=" + this.width +
                ", length=" + this.length +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
