package com.miguelsperle.nexbuy.core.infrastructure.adapters.out.security;

import com.miguelsperle.nexbuy.core.application.ports.out.security.ISecurityContextService;
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
