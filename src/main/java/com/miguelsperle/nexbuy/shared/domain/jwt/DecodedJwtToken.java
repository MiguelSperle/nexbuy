package com.miguelsperle.nexbuy.shared.domain.jwt;

public record DecodedJwtToken(
        String subject,
        String role
) {
}
