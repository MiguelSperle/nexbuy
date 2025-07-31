package com.miguelsperle.nexbuy.module.user.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    List<Address> findAll();
    Optional<Address> findById(String id);
    Address save(Address address);
    void deleteById(String id);
    List<Address> findAllByUserId(String userId);
}
