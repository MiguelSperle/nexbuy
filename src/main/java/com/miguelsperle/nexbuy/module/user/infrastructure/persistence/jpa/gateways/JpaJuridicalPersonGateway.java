package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IJuridicalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalPerson;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaJuridicalPersonEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaJuridicalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaJuridicalPersonGateway implements IJuridicalPersonGateway {
    private final JpaJuridicalPersonRepository jpaJuridicalPersonRepository;

    @Override
    public List<JuridicalPerson> findAll() {
        return this.jpaJuridicalPersonRepository.findAll().stream().map(JpaJuridicalPersonEntity::toEntity).toList();
    }

    @Override
    public Optional<JuridicalPerson> findById(String id) {
        return this.jpaJuridicalPersonRepository.findById(id).map(JpaJuridicalPersonEntity::toEntity);
    }

    @Override
    public JuridicalPerson save(JuridicalPerson juridicalPerson) {
        return this.jpaJuridicalPersonRepository.save(JpaJuridicalPersonEntity.from(juridicalPerson)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaJuridicalPersonRepository.deleteById(id);
    }
}
