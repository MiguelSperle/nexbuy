package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.product.domain.entities.ProductBrand;

import java.util.List;
import java.util.Optional;

public interface IProductBrandGateway {
    List<ProductBrand> findAll();
    Optional<ProductBrand> findById(String id);
    ProductBrand save(ProductBrand productBrand);
    void deleteById(String id);
}
