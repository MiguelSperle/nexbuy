package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaProductEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private JpaProductCategoryEntity jpaProductCategoryEntity;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(unique = true, nullable = false, length = 50)
    private String sku;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private JpaProductBrandEntity jpaProductBrandEntity;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private JpaProductModelEntity jpaProductModelEntity;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private JpaProductColorEntity jpaProductColorEntity;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaProductEntity from(Product product) {
        return new JpaProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                JpaProductCategoryEntity.from(product.getProductCategory()),
                product.getPrice(),
                product.getSku(),
                JpaProductBrandEntity.from(product.getProductBrand()),
                JpaProductModelEntity.from(product.getProductModel()),
                JpaProductColorEntity.from(product.getProductColor()),
                product.getIsActive(),
                product.getCreatedAt()
        );
    }

    public Product toEntity() {
        return Product.with(
                this.id,
                this.name,
                this.description,
                this.jpaProductCategoryEntity.toEntity(),
                this.price,
                this.sku,
                this.jpaProductBrandEntity.toEntity(),
                this.jpaProductModelEntity.toEntity(),
                this.jpaProductColorEntity.toEntity(),
                this.isActive,
                this.createdAt
        );
    }
}
