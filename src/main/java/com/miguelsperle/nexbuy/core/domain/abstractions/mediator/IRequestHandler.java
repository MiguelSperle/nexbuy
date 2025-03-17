package com.miguelsperle.nexbuy.core.domain.abstractions.mediator;

public interface IRequestHandler<IN extends IRequest<OUT>, OUT> {
    OUT handle(IN input);
}
