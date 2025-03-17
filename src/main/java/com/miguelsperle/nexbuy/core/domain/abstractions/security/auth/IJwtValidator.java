package com.miguelsperle.nexbuy.core.domain.abstractions.security.auth;

public interface IJwtValidator {
    String validateJWT(String token);
}
