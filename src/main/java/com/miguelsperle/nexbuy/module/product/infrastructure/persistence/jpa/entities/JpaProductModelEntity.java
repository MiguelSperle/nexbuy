package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.ProductModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_models")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaProductModelEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaProductModelEntity from(ProductModel productModel) {
        return new JpaProductModelEntity(
                productModel.getId(),
                productModel.getName(),
                productModel.getCreatedAt()
        );
    }

    public ProductModel toEntity() {
        return ProductModel.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
