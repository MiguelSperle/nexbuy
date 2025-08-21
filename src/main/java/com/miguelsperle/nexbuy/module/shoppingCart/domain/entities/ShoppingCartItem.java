package com.miguelsperle.nexbuy.module.shoppingCart.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShoppingCartItem {
    private final String id;
    private final String shoppingCartId;
    private final String productId;
    private final Integer quantity;
    private final BigDecimal unitPrice;
    private final LocalDateTime createdAt;

    private ShoppingCartItem(
            String id,
            String shoppingCartId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.createdAt = createdAt;
    }

    public static ShoppingCartItem newShoppingCartItem(
            String shoppingCartId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice
    ) {
        return new ShoppingCartItem(
                IdentifierUtils.generateUUID(),
                shoppingCartId,
                productId,
                quantity,
                unitPrice,
                TimeUtils.now()
        );
    }

    public static ShoppingCartItem with(
            String id,
            String shoppingCartId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice,
            LocalDateTime createdAt
    ) {
        return new ShoppingCartItem(
                id,
                shoppingCartId,
                productId,
                quantity,
                unitPrice,
                createdAt
        );
    }

    public ShoppingCartItem withQuantity(Integer quantity) {
        return new ShoppingCartItem(
                this.id,
                this.shoppingCartId,
                this.productId,
                quantity,
                this.unitPrice,
                this.createdAt
        );
    }

    public ShoppingCartItem withUnitPrice(BigDecimal unitPrice) {
        return new ShoppingCartItem(
                this.id,
                this.shoppingCartId,
                this.productId,
                this.quantity,
                unitPrice,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getShoppingCartId() {
        return this.shoppingCartId;
    }

    public String getProductId() {
        return this.productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "id='" + this.id + '\'' +
                ", shoppingCartId='" + this.shoppingCartId + '\'' +
                ", productId='" + this.productId + '\'' +
                ", quantity=" + this.quantity +
                ", unitPrice=" + this.unitPrice +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
