package com.miguelsperle.nexbuy.module.product.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface ColorRepository {
    List<Color> findAll();
    Optional<Color> findById(String id);
    Color save(Color color);
    void deleteById(String id);
    boolean existsByName(String name);
    Pagination<Color> findAllPaginated(SearchQuery searchQuery);
}
