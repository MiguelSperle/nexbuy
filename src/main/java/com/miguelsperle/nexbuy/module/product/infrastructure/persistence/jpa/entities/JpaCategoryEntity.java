package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class JpaCategoryEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaCategoryEntity from(Category category) {
        return new JpaCategoryEntity(
                category.getId(),
                category.getName(),
                category.getCreatedAt()
        );
    }

    public Category toEntity() {
        return Category.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
