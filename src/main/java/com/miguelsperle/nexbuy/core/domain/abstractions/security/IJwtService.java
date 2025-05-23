package com.miguelsperle.nexbuy.core.domain.abstractions.security;

public interface IJwtService {
    String generateJwt(String userId);
    String validateJwt(String jwtToken);
}

