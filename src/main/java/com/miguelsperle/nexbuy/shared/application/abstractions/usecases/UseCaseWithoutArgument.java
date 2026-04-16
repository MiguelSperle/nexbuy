package com.miguelsperle.nexbuy.shared.application.abstractions.usecases;

public interface UseCaseWithoutArgument<OUT> {
    OUT execute();
}
