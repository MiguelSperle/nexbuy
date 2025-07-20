package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class JpaProductEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "category_id", nullable = false, length = 36)
    private String categoryId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(unique = true, nullable = false, length = 80)
    private String sku;

    @Column(name = "brand_id", nullable = false, length = 36)
    private String brandId;

    @Column(name = "color_id", nullable = false, length = 36)
    private String colorId;

    @Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(nullable = false) // grams
    private Integer weight;

    @Column(nullable = false) // mm
    private Integer height;

    @Column(nullable = false) // mm
    private Integer width;

    @Column(nullable = false) // mm
    private Integer length;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaProductEntity from(Product product) {
        return new JpaProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategoryId(),
                product.getPrice(),
                product.getSku(),
                product.getBrandId(),
                product.getColorId(),
                product.getProductStatus(),
                product.getWeight(),
                product.getHeight(),
                product.getWidth(),
                product.getLength(),
                product.getCreatedAt()
        );
    }

    public Product toEntity() {
        return Product.with(
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
                this.length,
                this.createdAt
        );
    }
}
