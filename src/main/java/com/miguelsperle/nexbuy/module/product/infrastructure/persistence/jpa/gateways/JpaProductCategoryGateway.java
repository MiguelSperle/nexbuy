package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductCategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.ProductCategory;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductCategoryEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories.JpaProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaProductCategoryGateway implements IProductCategoryGateway {
    private final JpaProductCategoryRepository jpaProductCategoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        return this.jpaProductCategoryRepository.findAll().stream().map(JpaProductCategoryEntity::toEntity).toList();
    }

    @Override
    public Optional<ProductCategory> findById(String id) {
        return this.jpaProductCategoryRepository.findById(id).map(JpaProductCategoryEntity::toEntity);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return this.jpaProductCategoryRepository.save(JpaProductCategoryEntity.from(productCategory)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaProductCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<ProductCategory> findByName(String name) {
        return this.jpaProductCategoryRepository.findByName(name).map(JpaProductCategoryEntity::toEntity);
    }
}
