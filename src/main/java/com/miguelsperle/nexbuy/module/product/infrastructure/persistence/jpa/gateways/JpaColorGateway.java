package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaColorEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories.JpaColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaColorGateway implements IColorGateway {
    private final JpaColorRepository jpaColorRepository;

    @Override
    public List<Color> findAll() {
        return this.jpaColorRepository.findAll().stream().map(JpaColorEntity::toEntity).toList();
    }

    @Override
    public Optional<Color> findById(String id) {
        return this.jpaColorRepository.findById(id).map(JpaColorEntity::toEntity);
    }

    @Override
    public Color save(Color color) {
        return this.jpaColorRepository.save(JpaColorEntity.from(color)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaColorRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaColorRepository.existsByName(name);
    }
}
