package com.miguelsperle.nexbuy.core.infrastructure.providers;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeProvider implements ICodeProvider {
    private final SecureRandom secureRandom = new SecureRandom();

    private final static int CODE_LENGTH = 6;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    public String generateCode() {
        final StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(ALPHANUMERIC_CHARACTERS.charAt(this.secureRandom.nextInt(ALPHANUMERIC_CHARACTERS.length())));
        }

        return code.toString();
    }
}
