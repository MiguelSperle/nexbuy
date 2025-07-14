package com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways;

import java.util.List;
import java.util.Optional;

public interface IModelGateway {
    List<Model> findAll();
    Optional<Model> findById(String id);
    Model save(Model model);
    void deleteById(String id);
    boolean existsByName(String name);
}
