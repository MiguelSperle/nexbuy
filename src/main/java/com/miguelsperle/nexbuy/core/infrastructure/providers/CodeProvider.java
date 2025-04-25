package com.miguelsperle.nexbuy.core.infrastructure.providers;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.InvalidCharactersException;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.InvalidCodeLengthException;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeProvider implements ICodeProvider {
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateCode(int codeLength, String characters) {
        if (codeLength <= 0) {
            throw new InvalidCodeLengthException("Code length should be greater than zero");
        }

        if (characters == null || characters.isEmpty()) {
            throw new InvalidCharactersException("Characters should not be neither null nor empty");
        }

        final StringBuilder code = new StringBuilder(codeLength);

        for (int i = 0; i < codeLength; i++) {
            code.append(characters.charAt(this.secureRandom.nextInt(characters.length())));
        }

        return code.toString();
    }
}
