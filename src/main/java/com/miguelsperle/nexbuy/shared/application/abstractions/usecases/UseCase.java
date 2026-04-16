package com.miguelsperle.nexbuy.shared.application.abstractions.usecases;

public interface UseCase<IN, OUT> {
    OUT execute(IN input);
}
