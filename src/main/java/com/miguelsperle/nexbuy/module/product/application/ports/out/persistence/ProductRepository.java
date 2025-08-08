package com.miguelsperle.nexbuy.module.product.application.ports.out.persistence;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(String id);
    Product save(Product product);
    void deleteById(String id);
    boolean existsByBrandId(String brandId);
    boolean existsByCategoryId(String categoryId);
    boolean existsByColorId(String colorId);
    Pagination<Product> findAllPaginated(SearchQuery searchQuery);
}
