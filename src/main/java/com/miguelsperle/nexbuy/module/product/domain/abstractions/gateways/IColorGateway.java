package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

import java.util.List;
import java.util.Optional;

public interface IColorGateway {
    List<Color> findAll();
    Optional<Color> findById(String id);
    Color save(Color color);
    void deleteById(String id);
    boolean existsByName(String name);
}
