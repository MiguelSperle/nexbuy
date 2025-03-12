package com.example.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.example.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserGateway {
    List<User> findAll();
    Optional<User> findById(String id);
    User save(User user);
    void deleteById(String id);
}
