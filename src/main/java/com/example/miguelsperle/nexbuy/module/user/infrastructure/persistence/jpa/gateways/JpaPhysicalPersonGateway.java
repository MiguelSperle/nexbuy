package com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.example.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IPhysicalPersonGateway;
import com.example.miguelsperle.nexbuy.module.user.domain.entities.PhysicalPerson;
import com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaPhysicalPersonEntity;
import com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaPhysicalPersonRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaPhysicalPersonGateway implements IPhysicalPersonGateway {
    private final JpaPhysicalPersonRepository jpaPhysicalPersonRepository;

    @Override
    public List<PhysicalPerson> findAll() {
        return this.jpaPhysicalPersonRepository.findAll().stream().map(JpaPhysicalPersonEntity::toEntity).toList();
    }

    @Override
    public Optional<PhysicalPerson> findById(String id) {
        return this.jpaPhysicalPersonRepository.findById(id).map(JpaPhysicalPersonEntity::toEntity);
    }

    @Override
    public PhysicalPerson save(PhysicalPerson physicalPerson) {
        return this.jpaPhysicalPersonRepository.save(JpaPhysicalPersonEntity.from(physicalPerson)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaPhysicalPersonRepository.deleteById(id);
    }
}
