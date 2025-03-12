package com.example.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.example.miguelsperle.nexbuy.module.user.domain.entities.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressGateway {
    List<Address> findAll();
    Optional<Address> findById(String id);
    Address save(Address address);
    void deleteById(String id);
}
