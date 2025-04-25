package com.miguelsperle.nexbuy.core.domain.abstractions.providers;

public interface ICodeProvider {
    String generateCode(int codeLength, String characters);
}
