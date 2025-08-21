package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.entities.JpaShoppingCartItemEntity;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.repositories.JpaShoppingCartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShoppingCartItemRepositoryImpl implements ShoppingCartItemRepository {
    private final JpaShoppingCartItemRepository jpaShoppingCartItemRepository;

    @Override
    public List<ShoppingCartItem> findAll() {
        return this.jpaShoppingCartItemRepository.findAll().stream().map(JpaShoppingCartItemEntity::toEntity).toList();
    }

    @Override
    public Optional<ShoppingCartItem> findById(String id) {
        return this.jpaShoppingCartItemRepository.findById(id).map(JpaShoppingCartItemEntity::toEntity);
    }

    @Override
    public ShoppingCartItem save(ShoppingCartItem shoppingCartItem) {
        return this.jpaShoppingCartItemRepository.save(JpaShoppingCartItemEntity.from(shoppingCartItem)).toEntity();
    }

    @Override
    public List<ShoppingCartItem> saveAll(List<ShoppingCartItem> shoppingCartItems) {
        return this.jpaShoppingCartItemRepository.saveAll(shoppingCartItems.stream().map(JpaShoppingCartItemEntity::from)
                .toList()).stream().map(JpaShoppingCartItemEntity::toEntity).toList();
    }

    @Override
    public void deleteById(String id) {
        this.jpaShoppingCartItemRepository.deleteById(id);
    }

    @Override
    public Optional<ShoppingCartItem> findByShoppingCartIdAndProductId(String shoppingCartId, String productId) {
        return this.jpaShoppingCartItemRepository.findByShoppingCartIdAndProductId(shoppingCartId, productId).map(JpaShoppingCartItemEntity::toEntity);
    }

    @Override
    public List<ShoppingCartItem> findAllByShoppingCartId(String shoppingCartId) {
        return this.jpaShoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId).stream().map(JpaShoppingCartItemEntity::toEntity).toList();
    }

    @Override
    public List<ShoppingCartItem> findAllByProductId(String productId) {
        return this.jpaShoppingCartItemRepository.findAllByProductId(productId).stream().map(JpaShoppingCartItemEntity::toEntity).toList();
    }
}
