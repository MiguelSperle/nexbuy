package com.miguelsperle.nexbuy.module.product.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private final String id;
    private final String name;
    private final String description;
    private final String categoryId;
    private final BigDecimal price;
    private final String sku;
    private final String brandId;
    private final String colorId;
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
            String categoryId,
            BigDecimal price,
            String sku,
            String brandId,
            String colorId,
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
        this.categoryId = categoryId;
        this.price = price;
        this.sku = sku;
        this.brandId = brandId;
        this.colorId = colorId;
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
            String categoryId,
            BigDecimal price,
            String sku,
            String brandId,
            String colorId,
            Integer weight,
            Integer height,
            Integer width,
            Integer length
    ) {
        return new Product(
                IdentifierUtils.generateUUID(),
                name,
                description,
                categoryId,
                price,
                sku,
                brandId,
                colorId,
                ProductStatus.ACTIVE,
                weight,
                height,
                width,
                length,
                TimeUtils.now()
        );
    }

    public static Product with(
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
            Integer height,
            Integer width,
            Integer length,
            LocalDateTime createdAt
    ) {
        return new Product(
                id,
                name,
                description,
                categoryId,
                price,
                sku,
                brandId,
                colorId,
                productStatus,
                weight,
                height,
                width,
                length,
                createdAt
        );
    }

    public Product withName(String name) {
        return new Product(
                this.id,
                name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withDescription(String description) {
        return new Product(
                this.id,
                this.name,
                description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withCategory(String categoryId) {
        return new Product(
                this.id,
                this.name,
                this.description,
                categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withPrice(BigDecimal price) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withSku(String sku) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withBrand(String brandId) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withColor(String colorId) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withProductStatus(ProductStatus productStatus) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                productStatus,
                this.weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withWeight(Integer weight) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                weight,
                this.height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withHeight(Integer height) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                height,
                this.width,
                this.length,
                this.createdAt
        );
    }

    public Product withWidth(Integer width) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                width,
                this.length,
                this.createdAt
        );
    }

    public Product withLength(Integer length) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.categoryId,
                this.price,
                this.sku,
                this.brandId,
                this.colorId,
                this.productStatus,
                this.weight,
                this.height,
                this.width,
                length,
                this.createdAt
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

    public String getCategoryId() {
        return this.categoryId;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getSku() {
        return this.sku;
    }

    public String getBrandId() {
        return this.brandId;
    }

    public String getColorId() {
        return this.colorId;
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
                ", categoryId=" + this.categoryId +
                ", price=" + this.price +
                ", sku='" + this.sku + '\'' +
                ", brandId=" + this.brandId +
                ", colorId=" + this.colorId +
                ", productStatus=" + this.productStatus +
                ", weight=" + this.weight +
                ", height=" + this.height +
                ", width=" + this.width +
                ", length=" + this.length +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
