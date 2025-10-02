package com.miguelsperle.nexbuy.shared.infrastructure.adapters.out.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CodeProviderTest {
    private CodeProviderImpl codeProvider;

    @BeforeEach
    public void setUp() {
        codeProvider = new CodeProviderImpl();
    }

    @Test
    @DisplayName("Should generate code")
    public void should_generate_code() {
        final int length = 6;
        final String characters = "0123456789";

        final String codeGenerated = this.codeProvider.generateCode(length, characters);

        Assertions.assertNotNull(codeGenerated);
        Assertions.assertEquals(length, codeGenerated.length());
    }
}
