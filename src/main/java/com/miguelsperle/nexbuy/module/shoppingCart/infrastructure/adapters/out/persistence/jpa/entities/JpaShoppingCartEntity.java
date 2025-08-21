package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_carts")
@AllArgsConstructor
@NoArgsConstructor
public class JpaShoppingCartEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, unique = true, length = 36)
    private String userId;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaShoppingCartEntity from(ShoppingCart shoppingCart) {
        return new JpaShoppingCartEntity(
                shoppingCart.getId(),
                shoppingCart.getUserId(),
                shoppingCart.getTotalAmount(),
                shoppingCart.getCreatedAt()
        );
    }

    public ShoppingCart toEntity() {
        return ShoppingCart.with(
                this.id,
                this.userId,
                this.totalAmount,
                this.createdAt
        );
    }
}
