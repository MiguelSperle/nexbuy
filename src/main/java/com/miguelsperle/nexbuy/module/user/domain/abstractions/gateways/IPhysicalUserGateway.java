package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.PhysicalUser;

import java.util.List;
import java.util.Optional;

public interface IPhysicalUserGateway {
    List<PhysicalUser> findAll();
    Optional<PhysicalUser> findById(String id);
    PhysicalUser save(PhysicalUser physicalUser);
    void deleteById(String id);
    Optional<PhysicalUser> findByCpf(String cpf);
    Optional<PhysicalUser> findByGeneralRegister(String generalRegister);
}
