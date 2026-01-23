package com.miguelsperle.nexbuy.common.application.ports.out.services;

import com.miguelsperle.nexbuy.common.domain.jwt.DecodedJwtToken;

public interface JwtService {
    String generateJwt(String userId, String role);
    DecodedJwtToken validateJwt(String jwtToken);
}

