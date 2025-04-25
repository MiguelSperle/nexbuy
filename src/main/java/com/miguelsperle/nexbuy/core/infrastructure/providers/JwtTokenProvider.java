package com.miguelsperle.nexbuy.core.infrastructure.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IJwtTokenProvider;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.FailedJwtCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtTokenProvider implements IJwtTokenProvider {
    @Value("${spring.api.security.token.secret}")
    private String secret;

    @Override
    public String generateJwt(User user) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("nexbuy")
                    .withSubject(user.getId())
                    .withExpiresAt(this.genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException jwtCreationException) {
            throw new FailedJwtCreationException("Failed to generate JWT token", jwtCreationException);
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
