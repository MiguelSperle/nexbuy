package com.miguelsperle.nexbuy.core.infrastructure.security;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.auth.IJwtValidator;
import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.FailedJwtVerificationException;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final IJwtValidator jwtValidator;
    private final IUserGateway userGateway;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = this.recoverToken(request);

            if (token != null) {
                final String userId = this.jwtValidator.validateJWT(token);
                final User user = this.userGateway.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                this.setAuthentication(user);
            }

            filterChain.doFilter(request, response);
        } catch (FailedJwtVerificationException failedJwtVerificationException) {
            final ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(failedJwtVerificationException.getMessage(), HttpStatus.UNAUTHORIZED.value());
            final String jsonResponse = new ObjectMapper().writeValueAsString(errorMessageResponse);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(jsonResponse);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) return null;

        return authorizationHeader.replace("Bearer ", "");
    }

    private void setAuthentication(User user) {
        final List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getAuthorizationRole()));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
