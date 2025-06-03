package com.miguelsperle.nexbuy.core.application.usecases.abstractions;

public interface IUseCaseWithoutArgument<OUT> {
    OUT execute();
}
