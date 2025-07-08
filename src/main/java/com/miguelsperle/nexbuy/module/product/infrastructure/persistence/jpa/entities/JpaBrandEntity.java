package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "brands")
@AllArgsConstructor
@NoArgsConstructor
public class JpaBrandEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaBrandEntity from(Brand brand) {
        return new JpaBrandEntity(
                brand.getId(),
                brand.getName(),
                brand.getCreatedAt()
        );
    }

    public Brand toEntity() {
        return Brand.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
