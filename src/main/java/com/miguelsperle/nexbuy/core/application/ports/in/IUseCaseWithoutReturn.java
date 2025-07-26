package com.miguelsperle.nexbuy.core.application.ports.in;

public interface IUseCaseWithoutReturn<IN> {
    void execute(IN input);
}
