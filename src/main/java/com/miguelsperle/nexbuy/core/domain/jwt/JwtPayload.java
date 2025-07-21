package com.miguelsperle.nexbuy.core.domain.jwt;

public record JwtPayload(String subject, String role) {
    public static JwtPayload from(String subject, String role) {
        return new JwtPayload(subject, role);
    }
}
