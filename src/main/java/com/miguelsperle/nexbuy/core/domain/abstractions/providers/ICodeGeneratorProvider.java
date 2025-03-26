package com.miguelsperle.nexbuy.core.domain.abstractions.providers;

public interface ICodeGeneratorProvider {
    String generateCode(int codeLength, String characters);
}
