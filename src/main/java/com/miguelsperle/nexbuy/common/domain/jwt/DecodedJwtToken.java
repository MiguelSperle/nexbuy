package com.miguelsperle.nexbuy.common.domain.jwt;

public record DecodedJwtToken(
        String subject,
        String role
) {
}
