package com.miguelsperle.nexbuy.core.application.usecases.abstractions;

public interface IUseCaseWithoutReturn<IN> {
    void execute(IN input);
}
