package com.miguelsperle.nexbuy.module.product.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandRepository {
    List<Brand> findAll();
    Optional<Brand> findById(String id);
    Brand save(Brand brand);
    void deleteById(String id);
    boolean existsByName(String name);
}
