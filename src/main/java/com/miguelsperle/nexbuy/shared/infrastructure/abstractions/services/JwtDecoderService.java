package com.miguelsperle.nexbuy.shared.infrastructure.abstractions.services;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtDecoderService {
    DecodedJWT decodeJwt(String jwt);
}
