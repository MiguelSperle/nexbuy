package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.PhysicalPerson;

import java.util.List;
import java.util.Optional;

public interface IPhysicalPersonGateway {
    List<PhysicalPerson> findAll();
    Optional<PhysicalPerson> findById(String id);
    PhysicalPerson save(PhysicalPerson physicalPerson);
    void deleteById(String id);
}
