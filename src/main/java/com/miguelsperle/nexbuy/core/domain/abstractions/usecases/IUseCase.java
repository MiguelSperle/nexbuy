package com.miguelsperle.nexbuy.core.domain.abstractions.usecases;

public interface IUseCase<IN, OUT> {
    OUT execute(IN input);
}
