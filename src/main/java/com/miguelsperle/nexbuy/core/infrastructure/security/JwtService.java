package com.miguelsperle.nexbuy.core.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IJwtService;
import com.miguelsperle.nexbuy.core.domain.jwt.JwtPayload;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.JwtTokenCreationFailedException;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.JwtTokenValidationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService implements IJwtService {
    @Value("${spring.api.security.token.secret}")
    private String secret;

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
        } catch (JWTCreationException jwtCreationException) {
            throw new JwtTokenCreationFailedException("JWT token creation failed", jwtCreationException);
        }
    }

    @Override
    public JwtPayload validateJwt(String jwtToken) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            final DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer("nexbuy").build().verify(jwtToken);

            return JwtPayload.from(decodedJWT.getSubject(), decodedJWT.getClaim("role").asString());
        } catch (JWTVerificationException jwtVerificationException) {
            throw new JwtTokenValidationFailedException("Invalid JWT token", jwtVerificationException);
        }
    }

    private Instant genExpirationDate(Instant now) {
        return now.plus(5, ChronoUnit.MINUTES);
    }
}
