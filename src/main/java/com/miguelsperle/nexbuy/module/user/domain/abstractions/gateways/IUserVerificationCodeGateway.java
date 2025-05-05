package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;

import java.util.List;
import java.util.Optional;

public interface IUserVerificationCodeGateway {
    List<UserVerificationCode> findAll();
    Optional<UserVerificationCode> findById(String id);
    UserVerificationCode save(UserVerificationCode userVerificationCode);
    void deleteById(String id);
    Optional<UserVerificationCode> findByUserId(String userId);
    Optional<UserVerificationCode> findByCode(String code);
    void flush();
}
