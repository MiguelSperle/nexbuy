package com.miguelsperle.nexbuy.shared.application.ports.out.services;

import com.miguelsperle.nexbuy.shared.domain.jwt.DecodedJwtToken;

public interface JwtService {
    String generateJwt(String userId, String role);
    DecodedJwtToken validateJwt(String jwtToken);
}

