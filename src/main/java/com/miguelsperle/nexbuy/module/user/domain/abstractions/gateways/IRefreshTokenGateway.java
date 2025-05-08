package com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;

import java.util.List;
import java.util.Optional;

public interface IRefreshTokenGateway {
    List<RefreshToken> findAll();
    Optional<RefreshToken> findById(String id);
    RefreshToken save(RefreshToken refreshToken);
    void deleteById(String id);
    Optional<RefreshToken> findByUserId(String userId);
}
