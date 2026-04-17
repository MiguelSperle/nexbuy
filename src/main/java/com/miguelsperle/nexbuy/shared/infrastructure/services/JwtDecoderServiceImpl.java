package com.miguelsperle.nexbuy.shared.infrastructure.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.miguelsperle.nexbuy.shared.infrastructure.abstractions.services.JwtDecoderService;
import com.miguelsperle.nexbuy.shared.infrastructure.services.exceptions.JwtTokenDecodingFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtDecoderServiceImpl implements JwtDecoderService {
    @Value("${spring.api.security.token.secret}")
    private String secret;

    private static final Logger log = LoggerFactory.getLogger(JwtDecoderServiceImpl.class);

    @Override
    public DecodedJWT decodeJwt(final String jwt) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.require(algorithm).withIssuer("nexbuy").build().verify(jwt);
        } catch (final Exception ex) {
            log.error("Failed to decode JWT token", ex);
            throw JwtTokenDecodingFailedException.with("Invalid JWT token");
        }
    }
}
