package com.miguelsperle.nexbuy.core.infrastructure.security;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatedUserService implements IAuthenticatedUserService {

    @Override
    public Optional<String> getAuthenticatedUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return Optional.of((String) authentication.getPrincipal());
        }

        return Optional.empty();
    }
}
