package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_cart_items")
@AllArgsConstructor
@NoArgsConstructor
public class JpaShoppingCartItemEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "shopping_cart_id", nullable = false, length = 36)
    private String shoppingCartId;

    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaShoppingCartItemEntity from(ShoppingCartItem shoppingCartItem) {
        return new JpaShoppingCartItemEntity(
                shoppingCartItem.getId(),
                shoppingCartItem.getShoppingCartId(),
                shoppingCartItem.getProductId(),
                shoppingCartItem.getQuantity(),
                shoppingCartItem.getUnitPrice(),
                shoppingCartItem.getCreatedAt()
        );
    }

    public ShoppingCartItem toEntity() {
        return ShoppingCartItem.with(
                this.id,
                this.shoppingCartId,
                this.productId,
                this.quantity,
                this.unitPrice,
                this.createdAt
        );
    }
}
