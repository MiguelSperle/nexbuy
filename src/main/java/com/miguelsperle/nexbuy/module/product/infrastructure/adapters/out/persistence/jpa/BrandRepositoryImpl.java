package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaBrandEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.repositories.JpaBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {
    private final JpaBrandRepository jpaBrandRepository;

    @Override
    public List<Brand> findAll() {
        return this.jpaBrandRepository.findAll().stream().map(JpaBrandEntity::toEntity).toList();
    }

    @Override
    public Optional<Brand> findById(String id) {
        return this.jpaBrandRepository.findById(id).map(JpaBrandEntity::toEntity);
    }

    @Override
    public Brand save(Brand brand) {
        return this.jpaBrandRepository.save(JpaBrandEntity.from(brand)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaBrandRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaBrandRepository.existsByName(name);
    }
}
