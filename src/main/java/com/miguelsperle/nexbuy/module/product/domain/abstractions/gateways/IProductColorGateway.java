package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.product.domain.entities.ProductColor;

import java.util.List;
import java.util.Optional;

public interface IProductColorGateway {
    List<ProductColor> findAll();
    Optional<ProductColor> findById(String id);
    ProductColor save(ProductColor productColor);
    void deleteById(String id);
}
