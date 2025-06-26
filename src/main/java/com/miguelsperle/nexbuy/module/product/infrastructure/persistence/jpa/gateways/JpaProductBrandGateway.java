package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.ProductBrand;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductBrandEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories.JpaProductBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaProductBrandGateway implements IProductBrandGateway {
    private final JpaProductBrandRepository jpaProductBrandRepository;

    @Override
    public List<ProductBrand> findAll() {
        return this.jpaProductBrandRepository.findAll().stream().map(JpaProductBrandEntity::toEntity).toList();
    }

    @Override
    public Optional<ProductBrand> findById(String id) {
        return this.jpaProductBrandRepository.findById(id).map(JpaProductBrandEntity::toEntity);
    }

    @Override
    public ProductBrand save(ProductBrand productBrand) {
        return this.jpaProductBrandRepository.save(JpaProductBrandEntity.from(productBrand)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaProductBrandRepository.deleteById(id);
    }
}
