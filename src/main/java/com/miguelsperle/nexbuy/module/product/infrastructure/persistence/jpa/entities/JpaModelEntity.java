package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.product.domain.entities.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "models")
@AllArgsConstructor
@NoArgsConstructor
public class JpaModelEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaModelEntity from(Model model) {
        return new JpaModelEntity(
                model.getId(),
                model.getName(),
                model.getCreatedAt()
        );
    }

    public Model toEntity() {
        return Model.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
