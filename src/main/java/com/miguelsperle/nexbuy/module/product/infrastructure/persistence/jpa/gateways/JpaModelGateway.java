package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IModelGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Model;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaModelEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories.JpaModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaModelGateway implements IModelGateway {
    private final JpaModelRepository jpaModelRepository;

    @Override
    public List<Model> findAll() {
        return this.jpaModelRepository.findAll().stream().map(JpaModelEntity::toEntity).toList();
    }

    @Override
    public Optional<Model> findById(String id) {
        return this.jpaModelRepository.findById(id).map(JpaModelEntity::toEntity);
    }

    @Override
    public Model save(Model model) {
        return this.jpaModelRepository.save(JpaModelEntity.from(model)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaModelRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaModelRepository.existsByName(name);
    }
}
