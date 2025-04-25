package com.miguelsperle.nexbuy.core.infrastructure.security.validators;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.validators.IJwtTokenValidator;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.FailedJwtVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenValidator implements IJwtTokenValidator {
    @Value("${spring.api.security.token.secret}")
    private String secret;

    @Override
    public String validateJwt(String token) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.require(algorithm)
                    .withIssuer("nexbuy")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException jwtVerificationException) {
            throw new FailedJwtVerificationException("Invalid JWT token", jwtVerificationException);
        }
    }
}
