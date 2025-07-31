package com.miguelsperle.nexbuy.module.user.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;

import java.util.List;
import java.util.Optional;

public interface NaturalPersonRepository {
    List<NaturalPerson> findAll();
    Optional<NaturalPerson> findById(String id);
    NaturalPerson save(NaturalPerson naturalPerson);
    void deleteById(String id);
    boolean existsByCpf(String cpf);
    boolean existsByGeneralRegister(String generalRegister);
    Optional<NaturalPerson> findByUserId(String userId);
}
