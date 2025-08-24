package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.entities.JpaShoppingCartEntity;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.repositories.JpaShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private final JpaShoppingCartRepository jpaShoppingCartRepository;

    @Override
    public List<ShoppingCart> findAll() {
        return this.jpaShoppingCartRepository.findAll().stream().map(JpaShoppingCartEntity::toEntity).toList();
    }

    @Override
    public Optional<ShoppingCart> findById(String id) {
        return this.jpaShoppingCartRepository.findById(id).map(JpaShoppingCartEntity::toEntity);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return this.jpaShoppingCartRepository.save(JpaShoppingCartEntity.from(shoppingCart)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaShoppingCartRepository.deleteById(id);
    }
}
