package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.ProductBrand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_brands")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaProductBrandEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaProductBrandEntity from(ProductBrand productBrand) {
        return new JpaProductBrandEntity(
                productBrand.getId(),
                productBrand.getName(),
                productBrand.getCreatedAt()
        );
    }

    public ProductBrand toEntity() {
        return ProductBrand.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
