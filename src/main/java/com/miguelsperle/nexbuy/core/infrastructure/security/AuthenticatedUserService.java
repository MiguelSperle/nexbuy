package com.miguelsperle.nexbuy.core.infrastructure.security;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.InvalidPrincipalTypeException;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService implements IAuthenticatedUserService {

    @Override
    public User getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final Object principal = authentication.getPrincipal();

        if (!(principal instanceof User)) {
            throw new InvalidPrincipalTypeException("Principal is not of type User");
        }

        return (User) principal;
    }
}
