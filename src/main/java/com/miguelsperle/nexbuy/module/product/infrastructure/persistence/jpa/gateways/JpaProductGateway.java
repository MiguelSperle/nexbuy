package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaProductGateway implements IProductGateway {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public List<Product> findAll() {
        return this.jpaProductRepository.findAll().stream().map(JpaProductEntity::toEntity).toList();
    }

    @Override
    public Optional<Product> findById(String id) {
        return this.jpaProductRepository.findById(id).map(JpaProductEntity::toEntity);
    }

    @Override
    public Product save(Product product) {
        return this.jpaProductRepository.save(JpaProductEntity.from(product)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaProductRepository.deleteById(id);
    }

    @Override
    public boolean existsByBrandId(String brandId) {
        return this.jpaProductRepository.existsByBrandId(brandId);
    }
}
