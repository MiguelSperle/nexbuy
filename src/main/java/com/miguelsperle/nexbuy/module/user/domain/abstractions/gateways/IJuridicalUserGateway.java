package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalUser;

import java.util.List;
import java.util.Optional;

public interface IJuridicalUserGateway {
    List<JuridicalUser> findAll();
    Optional<JuridicalUser> findById(String id);
    JuridicalUser save(JuridicalUser juridicalUser);
    void deleteById(String id);
}
