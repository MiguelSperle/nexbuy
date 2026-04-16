package com.miguelsperle.nexbuy.shared.application.abstractions.usecases;

public interface UseCaseWithoutReturn<IN> {
    void execute(IN input);
}
