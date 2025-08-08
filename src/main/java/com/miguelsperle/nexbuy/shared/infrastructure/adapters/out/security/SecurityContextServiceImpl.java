package com.miguelsperle.nexbuy.shared.infrastructure.adapters.out.security;

import com.miguelsperle.nexbuy.shared.application.ports.out.security.SecurityContextService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {
    @Override
    public String getAuthenticatedUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (String) authentication.getPrincipal();
    }
}
