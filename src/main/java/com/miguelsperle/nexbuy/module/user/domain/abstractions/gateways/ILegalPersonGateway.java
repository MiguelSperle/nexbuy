package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;

import java.util.List;
import java.util.Optional;

public interface ILegalPersonGateway {
    List<LegalPerson> findAll();
    Optional<LegalPerson> findById(String id);
    LegalPerson save(LegalPerson legalPerson);
    void deleteById(String id);
    Optional<LegalPerson> findByCnpj(String cnpj);
    Optional<LegalPerson> findByFantasyName(String fantasyName);
    Optional<LegalPerson> findByLegalName(String legalName);
    Optional<LegalPerson> findByStateRegistration(String stateRegistration);
    Optional<LegalPerson> findByUserId(String userId);
}
