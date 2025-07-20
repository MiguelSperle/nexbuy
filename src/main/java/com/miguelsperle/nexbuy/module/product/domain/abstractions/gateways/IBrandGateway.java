package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandGateway {
    List<Brand> findAll();
    Optional<Brand> findById(String id);
    Brand save(Brand brand);
    void deleteById(String id);
    boolean existsByName(String name);
    List<Brand> findAllByIds(List<String> ids);
}
