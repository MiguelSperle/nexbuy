package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaCategoryEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false, length = 50)
    private String slug;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private JpaCategoryEntity jpaCategoryEntity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaCategoryEntity from(Category category) {
        return new JpaCategoryEntity(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getSlug(),
                category.getParentCategory() != null ? JpaCategoryEntity.from(category.getParentCategory()) : null,
                category.getCreatedAt()
        );
    }

    public Category toEntity() {
        return Category.with(
                this.id,
                this.name,
                this.description,
                this.slug,
                this.jpaCategoryEntity != null ? this.jpaCategoryEntity.toEntity() : null,
                this.createdAt
        );
    }
}
