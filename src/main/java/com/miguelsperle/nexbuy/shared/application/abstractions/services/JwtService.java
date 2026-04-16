package com.miguelsperle.nexbuy.shared.application.abstractions.services;

import com.miguelsperle.nexbuy.shared.domain.jwt.DecodedJwtToken;

public interface JwtService {
    String generateJwt(String userId, String role);
    DecodedJwtToken validateJwt(String jwtToken);
}

