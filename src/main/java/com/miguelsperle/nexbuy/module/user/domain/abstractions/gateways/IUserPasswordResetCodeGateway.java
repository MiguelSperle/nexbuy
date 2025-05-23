package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserPasswordResetCode;

import java.util.List;
import java.util.Optional;

public interface IUserPasswordResetCodeGateway {
    List<UserPasswordResetCode> findAll();
    Optional<UserPasswordResetCode> findById(String id);
    UserPasswordResetCode save(UserPasswordResetCode userPasswordResetCode);
    void deleteById(String id);
}
