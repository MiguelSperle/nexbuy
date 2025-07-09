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
    private final Model model;
    private final Color color;
    private final ProductStatus productStatus;
    private final LocalDateTime createdAt;

    private Product(
            String id,
            String name,
            String description,
            Category category,
            BigDecimal price,
            String sku,
            Brand brand,
            Model model,
            Color color,
            ProductStatus productStatus,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.sku = sku;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.productStatus = productStatus;
        this.createdAt = createdAt;
    }

    public static Product newProduct(
            String name,
            String description,
            Category category,
            BigDecimal price,
            String sku,
            Brand brand,
            Model model,
            Color color
    ) {
        return new Product(
                UUID.randomUUID().toString(),
                name,
                description,
                category,
                price,
                sku,
                brand,
                model,
                color,
                ProductStatus.ACTIVE,
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
            Model model,
            Color color,
            ProductStatus productStatus,
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
                model,
                color,
                productStatus,
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

    public Model getModel() {
        return this.model;
    }

    public Color getColor() {
        return this.color;
    }

    public ProductStatus getProductStatus() {
        return this.productStatus;
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
                ", model=" + this.model +
                ", color=" + this.color +
                ", productStatus=" + this.productStatus +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
