package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.entities.JpaShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaShoppingCartRepository extends JpaRepository<JpaShoppingCartEntity, String> {
}
