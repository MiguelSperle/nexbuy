package com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository {
    List<ShoppingCart> findAll();
    Optional<ShoppingCart> findById(String id);
    ShoppingCart save(ShoppingCart shoppingCart);
    void deleteById(String id);
    Optional<ShoppingCart> findByUserId(String userId);
}
