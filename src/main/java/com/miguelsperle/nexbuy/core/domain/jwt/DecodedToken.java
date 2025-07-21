package com.miguelsperle.nexbuy.core.domain.jwt;

public record DecodedToken(
        String subject,
        String role
) {
    public static DecodedToken from(String subject, String role) {
        return new DecodedToken(subject, role);
    }
}
