package com.miguelsperle.nexbuy.core.application.ports.in;

public interface IUseCase<IN, OUT> {
    OUT execute(IN input);
}
