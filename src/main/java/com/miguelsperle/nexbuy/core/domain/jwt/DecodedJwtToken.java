package com.miguelsperle.nexbuy.core.domain.jwt;

public record DecodedJwtToken(
        String subject,
        String role
) {
}
