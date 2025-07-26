package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaCategoryEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.repositories.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryRepository implements ICategoryRepository {
    private final JpaCategoryRepository jpaCategoryRepository;

    @Override
    public List<Category> findAll() {
        return this.jpaCategoryRepository.findAll().stream().map(JpaCategoryEntity::toEntity).toList();
    }

    @Override
    public Optional<Category> findById(String id) {
        return this.jpaCategoryRepository.findById(id).map(JpaCategoryEntity::toEntity);
    }

    @Override
    public Category save(Category category) {
        return this.jpaCategoryRepository.save(JpaCategoryEntity.from(category)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaCategoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaCategoryRepository.existsByName(name);
    }
}
