package com.miguelsperle.nexbuy.module.user.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;

import java.util.List;
import java.util.Optional;

public interface LegalPersonRepository {
    List<LegalPerson> findAll();
    Optional<LegalPerson> findById(String id);
    LegalPerson save(LegalPerson legalPerson);
    void deleteById(String id);
    boolean existsByCnpj(String cnpj);
    boolean existsByFantasyName(String fantasyName);
    boolean existsByLegalName(String legalName);
    boolean existsByStateRegistration(String stateRegistration);
    Optional<LegalPerson> findByUserId(String userId);
}
