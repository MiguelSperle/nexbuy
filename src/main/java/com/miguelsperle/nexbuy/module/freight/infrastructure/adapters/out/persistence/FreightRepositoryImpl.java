package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.freight.application.ports.out.persistence.FreightRepository;
import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;
import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence.jpa.entities.JpaFreightEntity;
import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence.jpa.repositories.JpaFreightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FreightRepositoryImpl implements FreightRepository {
    private final JpaFreightRepository jpaFreightRepository;

    @Override
    public List<Freight> findAll() {
        return this.jpaFreightRepository.findAll().stream().map(JpaFreightEntity::toEntity).toList();
    }

    @Override
    public Optional<Freight> findById(String id) {
        return this.jpaFreightRepository.findById(id).map(JpaFreightEntity::toEntity);
    }

    @Override
    public Freight save(Freight freight) {
        return this.jpaFreightRepository.save(JpaFreightEntity.from(freight)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaFreightRepository.deleteById(id);
    }

    @Override
    public Optional<Freight> findByOrderId(String orderId) {
        return this.jpaFreightRepository.findByOrderId(orderId).map(JpaFreightEntity::toEntity);
    }
}
