package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;

import java.util.List;
import java.util.Optional;

public interface INaturalPersonGateway {
    List<NaturalPerson> findAll();
    Optional<NaturalPerson> findById(String id);
    NaturalPerson save(NaturalPerson naturalPerson);
    void deleteById(String id);
    Optional<NaturalPerson> findByCpf(String cpf);
    Optional<NaturalPerson> findByGeneralRegister(String generalRegister);
    Optional<NaturalPerson> findByUserId(String userId);
}
