package com.miguelsperle.nexbuy.module.user.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;

import java.util.List;
import java.util.Optional;

public interface UserCodeRepository {
    List<UserCode> findAll();
    Optional<UserCode> findById(String id);
    UserCode save(UserCode userCode);
    void deleteById(String id);
    Optional<UserCode> findByUserIdAndCodeType(String userId, String codeType);
    Optional<UserCode> findByCodeAndCodeType(String code, String codeType);
}
