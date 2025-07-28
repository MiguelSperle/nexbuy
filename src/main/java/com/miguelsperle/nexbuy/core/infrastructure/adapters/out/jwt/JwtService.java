package com.miguelsperle.nexbuy.core.infrastructure.adapters.out.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.miguelsperle.nexbuy.core.application.ports.out.jwt.IJwtService;
import com.miguelsperle.nexbuy.core.domain.jwt.DecodedJwtToken;
import com.miguelsperle.nexbuy.core.infrastructure.adapters.exceptions.JwtTokenCreationFailedException;
import com.miguelsperle.nexbuy.core.infrastructure.adapters.exceptions.JwtTokenValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService implements IJwtService {
    @Value("${spring.api.security.token.secret}")
    private String secret;

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Override
    public String generateJwt(String userId, String role) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("nexbuy")
                    .withSubject(userId)
                    .withClaim("role", role)
                    .withExpiresAt(this.genExpirationDate(Instant.now()))
                    .sign(algorithm);
        } catch (Exception exception) {
            log.error("Failed to create JWT token for userId: [{}] with role: [{}]", userId, role, exception);
            throw JwtTokenCreationFailedException.with("An authentication error occurred");
        }
    }

    @Override
    public DecodedJwtToken validateJwt(String jwtToken) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            final DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer("nexbuy").build().verify(jwtToken);

            return new DecodedJwtToken(decodedJWT.getSubject(), decodedJWT.getClaim("role").asString());
        } catch (Exception exception) {
            log.error("Failed to validate JWT token", exception);
            throw JwtTokenValidationFailedException.with("Invalid JWT token");
        }
    }

    private Instant genExpirationDate(Instant now) {
        return now.plus(5, ChronoUnit.MINUTES);
    }
}
