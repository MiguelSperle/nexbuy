package com.miguelsperle.nexbuy.shared.application.abstractions.services;

public interface JwtGeneratorService {
    String generateJwt(String userId, String role);
}
