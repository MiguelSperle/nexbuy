package com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemRepository {
    List<ShoppingCartItem> findAll();
    Optional<ShoppingCartItem> findById(String id);
    ShoppingCartItem save(ShoppingCartItem shoppingCartItem);
    List<ShoppingCartItem> saveAll(List<ShoppingCartItem> shoppingCartItems);
    void deleteById(String id);
    Optional<ShoppingCartItem> findByShoppingCartIdAndProductId(String shoppingCartId, String productId);
    List<ShoppingCartItem> findAllByShoppingCartId(String shoppingCartId);
    List<ShoppingCartItem> findAllByProductId(String productId);
}
