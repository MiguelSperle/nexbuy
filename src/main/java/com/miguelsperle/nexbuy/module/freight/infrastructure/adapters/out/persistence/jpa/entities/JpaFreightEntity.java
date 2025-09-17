package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "freights")
@AllArgsConstructor
@NoArgsConstructor
public class JpaFreightEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "order_id", unique = true, nullable = false, length = 36)
    private String orderId;

    @Column(nullable = false, length = 12)
    private String name;

    @Column(name = "company_name", nullable = false, length = 20)
    private String companyName;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "estimated_time", nullable = false)
    private Integer estimatedTime;

    @Column(name = "min_time", nullable = false)
    private Integer minTime;

    @Column(name = "max_time", nullable = false)
    private Integer maxTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaFreightEntity from(Freight freight) {
        return new JpaFreightEntity(
                freight.getId(),
                freight.getOrderId(),
                freight.getName(),
                freight.getCompanyName(),
                freight.getPrice(),
                freight.getEstimatedTime(),
                freight.getMinTime(),
                freight.getMaxTime(),
                freight.getCreatedAt()
        );
    }

    public Freight toEntity() {
        return Freight.with(
                this.id,
                this.orderId,
                this.name,
                this.companyName,
                this.price,
                this.estimatedTime,
                this.minTime,
                this.maxTime,
                this.createdAt
        );
    }
}
