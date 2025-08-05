package com.miguelsperle.nexbuy.shared.infrastructure.adapters.out.providers;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.CodeProvider;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeProviderImpl implements CodeProvider {
    private final SecureRandom secureRandom = new SecureRandom();

    private final static int CODE_LENGTH = 6;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    public String generateCode() {
        final StringBuilder code = new StringBuilder(CODE_LENGTH);
        final int charSetLength = ALPHANUMERIC_CHARACTERS.length();

        for (int i = 0; i < CODE_LENGTH; i++) {
            final int randomIndex = this.secureRandom.nextInt(charSetLength);
            code.append(ALPHANUMERIC_CHARACTERS.charAt(randomIndex));
        }

        return code.toString();
    }
}
