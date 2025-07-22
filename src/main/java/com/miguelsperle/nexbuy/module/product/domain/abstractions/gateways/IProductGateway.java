package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductGateway {
    List<Product> findAll();
    Optional<Product> findById(String id);
    Product save(Product product);
    void deleteById(String id);
    boolean existsByBrandId(String brandId);
    boolean existsByCategoryId(String categoryId);
    boolean existsByColorId(String colorId);
    Pagination<Product> findAllPaginated(SearchQuery searchQuery);
}
