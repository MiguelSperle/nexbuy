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

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private JpaCategoryEntity jpaCategoryEntity;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(unique = true, nullable = false, length = 50)
    private String sku;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private JpaBrandEntity jpaBrandEntity;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private JpaModelEntity jpaModelEntity;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private JpaColorEntity jpaColorEntity;

    @Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaProductEntity from(Product product) {
        return new JpaProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                JpaCategoryEntity.from(product.getCategory()),
                product.getPrice(),
                product.getSku(),
                JpaBrandEntity.from(product.getBrand()),
                JpaModelEntity.from(product.getModel()),
                JpaColorEntity.from(product.getColor()),
                product.getProductStatus(),
                product.getCreatedAt()
        );
    }

    public Product toEntity() {
        return Product.with(
                this.id,
                this.name,
                this.description,
                this.jpaCategoryEntity.toEntity(),
                this.price,
                this.sku,
                this.jpaBrandEntity.toEntity(),
                this.jpaModelEntity.toEntity(),
                this.jpaColorEntity.toEntity(),
                this.productStatus,
                this.createdAt
        );
    }
}
