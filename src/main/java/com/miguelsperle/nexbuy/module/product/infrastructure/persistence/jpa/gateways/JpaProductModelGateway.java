package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductModelGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.ProductModel;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductModelEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories.JpaProductModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaProductModelGateway implements IProductModelGateway {
    private final JpaProductModelRepository jpaProductModelRepository;

    @Override
    public List<ProductModel> findAll() {
        return this.jpaProductModelRepository.findAll().stream().map(JpaProductModelEntity::toEntity).toList();
    }

    @Override
    public Optional<ProductModel> findById(String id) {
        return this.jpaProductModelRepository.findById(id).map(JpaProductModelEntity::toEntity);
    }

    @Override
    public ProductModel save(ProductModel productModel) {
        return this.jpaProductModelRepository.save(JpaProductModelEntity.from(productModel)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaProductModelRepository.deleteById(id);
    }
}
