package com.miguelsperle.nexbuy.core.application.usecases.abstractions;

public interface IUseCase<IN, OUT> {
    OUT execute(IN input);
}
