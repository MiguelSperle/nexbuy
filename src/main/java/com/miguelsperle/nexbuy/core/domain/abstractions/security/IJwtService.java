package com.miguelsperle.nexbuy.core.domain.abstractions.security;

import com.miguelsperle.nexbuy.core.domain.jwt.JwtPayload;

public interface IJwtService {
    String generateJwt(String userId, String role);
    JwtPayload validateJwt(String jwtToken);
}

