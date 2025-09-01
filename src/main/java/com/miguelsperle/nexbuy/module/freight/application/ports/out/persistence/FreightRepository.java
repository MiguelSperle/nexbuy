package com.miguelsperle.nexbuy.module.freight.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;

import java.util.List;
import java.util.Optional;

public interface FreightRepository {
    List<Freight> findAll();
    Optional<Freight> findById(String id);
    Freight save(Freight freight);
    void deleteById(String id);
}
