package com.miguelsperle.nexbuy.core.infrastructure.adapters.out.providers;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.IPasswordEncryptorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncryptorProvider implements IPasswordEncryptorProvider {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        return this.passwordEncoder.matches(password, encodedPassword);
    }
}
