package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_deliveries")
@AllArgsConstructor
@NoArgsConstructor
public class JpaOrderDeliveryEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "order_id", unique = true, nullable = false, length = 36)
    private String orderId;

    @Column(name = "address_line", nullable = false, length = 100)
    private String addressLine;

    @Column(name = "address_number", nullable = false, length = 15)
    private String addressNumber;

    @Column(name = "zip_code", nullable = false, length = 9)
    private String zipCode;

    @Column(nullable = false, length = 40)
    private String neighborhood;

    @Column(nullable = false, length = 40)
    private String city;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(length = 100)
    private String complement;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaOrderDeliveryEntity from(OrderDelivery orderDelivery) {
        return new JpaOrderDeliveryEntity(
                orderDelivery.getId(),
                orderDelivery.getOrderId(),
                orderDelivery.getAddressLine(),
                orderDelivery.getAddressNumber(),
                orderDelivery.getZipCode(),
                orderDelivery.getNeighborhood(),
                orderDelivery.getCity(),
                orderDelivery.getUf(),
                orderDelivery.getComplement(),
                orderDelivery.getCreatedAt()
        );
    }

    public OrderDelivery toEntity() {
        return OrderDelivery.with(
                this.id,
                this.orderId,
                this.addressLine,
                this.addressNumber,
                this.zipCode,
                this.neighborhood,
                this.city,
                this.uf,
                this.complement,
                this.createdAt
        );
    }
}
