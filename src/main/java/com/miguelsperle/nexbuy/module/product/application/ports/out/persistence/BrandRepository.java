package com.miguelsperle.nexbuy.module.product.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    List<Brand> findAll();
    Optional<Brand> findById(String id);
    Brand save(Brand brand);
    void deleteById(String id);
    boolean existsByName(String name);
    Pagination<Brand> findAllPaginated(SearchQuery searchQuery);
}
