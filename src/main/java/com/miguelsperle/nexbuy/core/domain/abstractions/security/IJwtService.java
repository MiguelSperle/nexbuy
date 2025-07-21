package com.miguelsperle.nexbuy.core.domain.abstractions.security;

import com.miguelsperle.nexbuy.core.domain.jwt.DecodedToken;

public interface IJwtService {
    String generateJwt(String userId, String role);
    DecodedToken validateJwt(String jwtToken);
}

