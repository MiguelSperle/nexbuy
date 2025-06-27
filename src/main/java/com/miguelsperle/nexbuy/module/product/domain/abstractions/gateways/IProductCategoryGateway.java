package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.product.domain.entities.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface IProductCategoryGateway {
    List<ProductCategory> findAll();
    Optional<ProductCategory> findById(String id);
    ProductCategory save(ProductCategory productCategory);
    void deleteById(String id);
    Optional<ProductCategory> findByName(String name);
}
