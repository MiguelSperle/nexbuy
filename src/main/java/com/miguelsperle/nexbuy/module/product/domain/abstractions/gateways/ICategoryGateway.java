package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryGateway {
    List<Category> findAll();
    Optional<Category> findById(String id);
    Category save(Category category);
    void deleteById(String id);
    Optional<Category> findByName(String name);
}
