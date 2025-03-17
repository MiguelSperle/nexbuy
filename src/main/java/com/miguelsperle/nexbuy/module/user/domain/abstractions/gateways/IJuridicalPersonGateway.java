package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalPerson;

import java.util.List;
import java.util.Optional;

public interface IJuridicalPersonGateway {
    List<JuridicalPerson> findAll();
    Optional<JuridicalPerson> findById(String id);
    JuridicalPerson save(JuridicalPerson juridicalPerson);
    void deleteById(String id);
}
