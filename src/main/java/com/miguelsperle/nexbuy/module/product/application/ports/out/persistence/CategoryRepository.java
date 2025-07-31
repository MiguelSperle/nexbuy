package com.miguelsperle.nexbuy.module.product.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(String id);
    Category save(Category category);
    void deleteById(String id);
    boolean existsByName(String name);
}
