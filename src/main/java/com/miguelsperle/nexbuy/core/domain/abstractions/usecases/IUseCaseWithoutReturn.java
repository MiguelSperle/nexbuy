package com.miguelsperle.nexbuy.core.domain.abstractions.usecases;

public interface IUseCaseWithoutReturn<IN> {
    void execute(IN input);
}
