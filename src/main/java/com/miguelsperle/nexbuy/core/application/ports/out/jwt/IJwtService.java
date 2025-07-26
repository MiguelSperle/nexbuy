package com.miguelsperle.nexbuy.core.application.ports.out.jwt;

import com.miguelsperle.nexbuy.core.domain.jwt.DecodedJwtToken;

public interface IJwtService {
    String generateJwt(String userId, String role);
    DecodedJwtToken validateJwt(String jwtToken);
}

