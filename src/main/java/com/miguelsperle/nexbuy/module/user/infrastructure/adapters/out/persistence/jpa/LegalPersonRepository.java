package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.ILegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.entities.JpaLegalPersonEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.repositories.JpaLegalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LegalPersonRepository implements ILegalPersonRepository {
    private final JpaLegalPersonRepository jpaLegalPersonRepository;

    @Override
    public List<LegalPerson> findAll() {
        return this.jpaLegalPersonRepository.findAll().stream().map(JpaLegalPersonEntity::toEntity).toList();
    }

    @Override
    public Optional<LegalPerson> findById(String id) {
        return this.jpaLegalPersonRepository.findById(id).map(JpaLegalPersonEntity::toEntity);
    }

    @Override
    public LegalPerson save(LegalPerson legalPerson) {
        return this.jpaLegalPersonRepository.save(JpaLegalPersonEntity.from(legalPerson)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaLegalPersonRepository.deleteById(id);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return this.jpaLegalPersonRepository.existsByCnpj(cnpj);
    }

    @Override
    public boolean existsByFantasyName(String fantasyName) {
        return this.jpaLegalPersonRepository.existsByFantasyName(fantasyName);
    }

    @Override
    public boolean existsByLegalName(String legalName) {
        return this.jpaLegalPersonRepository.existsByLegalName(legalName);
    }

    @Override
    public boolean existsByStateRegistration(String stateRegistration) {
        return this.jpaLegalPersonRepository.existsByStateRegistration(stateRegistration);
    }

    @Override
    public Optional<LegalPerson> findByUserId(String userId) {
        return this.jpaLegalPersonRepository.findByUserId(userId).map(JpaLegalPersonEntity::toEntity);
    }
}
