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
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }

    public Brand getBrand() {
        return brand;
    }

    public Model getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                ", brand=" + brand +
                ", model=" + model +
                ", color=" + color +
                ", productStatus=" + productStatus +
                ", createdAt=" + createdAt +
                '}';
    }
}
