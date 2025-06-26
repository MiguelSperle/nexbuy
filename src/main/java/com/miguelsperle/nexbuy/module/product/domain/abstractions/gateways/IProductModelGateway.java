package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.product.domain.entities.ProductModel;

import java.util.List;
import java.util.Optional;

public interface IProductModelGateway {
    List<ProductModel> findAll();
    Optional<ProductModel> findById(String id);
    ProductModel save(ProductModel productModel);
    void deleteById(String id);
}
