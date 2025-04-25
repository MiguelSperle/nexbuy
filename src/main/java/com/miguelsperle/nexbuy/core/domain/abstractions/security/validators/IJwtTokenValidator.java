package com.miguelsperle.nexbuy.core.domain.abstractions.security.validators;

public interface IJwtTokenValidator {
    String validateJwt(String token);
}
