package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.ProductColor;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductColorEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories.JpaProductColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaProductColorGateway implements IProductColorGateway {
    private final JpaProductColorRepository jpaProductColorRepository;

    @Override
    public List<ProductColor> findAll() {
        return this.jpaProductColorRepository.findAll().stream().map(JpaProductColorEntity::toEntity).toList();
    }

    @Override
    public Optional<ProductColor> findById(String id) {
        return this.jpaProductColorRepository.findById(id).map(JpaProductColorEntity::toEntity);
    }

    @Override
    public ProductColor save(ProductColor productColor) {
        return this.jpaProductColorRepository.save(JpaProductColorEntity.from(productColor)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaProductColorRepository.deleteById(id);
    }
}
