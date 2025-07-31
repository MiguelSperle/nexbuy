package com.miguelsperle.nexbuy.module.user.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(String id);
    User save(User user);
    void deleteById(String id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
