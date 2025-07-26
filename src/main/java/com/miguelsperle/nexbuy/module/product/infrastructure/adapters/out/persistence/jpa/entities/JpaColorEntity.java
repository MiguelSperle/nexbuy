package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "colors")
@AllArgsConstructor
@NoArgsConstructor
public class JpaColorEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 25)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaColorEntity from(Color color) {
        return new JpaColorEntity(
                color.getId(),
                color.getName(),
                color.getCreatedAt()
        );
    }

    public Color toEntity() {
        return Color.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
