package com.miguelsperle.nexbuy.core.infrastructure.security;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.ISecurityContextService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService implements ISecurityContextService {

    @Override
    public String getAuthenticatedUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (String) authentication.getPrincipal();
    }
}
