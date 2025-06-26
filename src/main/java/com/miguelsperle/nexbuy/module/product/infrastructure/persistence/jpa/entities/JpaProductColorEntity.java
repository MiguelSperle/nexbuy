package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.ProductColor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaProductColorEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 25)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaProductColorEntity from(ProductColor productColor) {
        return new JpaProductColorEntity(
                productColor.getId(),
                productColor.getName(),
                productColor.getCreatedAt()
        );
    }

    public ProductColor toEntity() {
        return ProductColor.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
