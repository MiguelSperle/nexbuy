package com.miguelsperle.nexbuy.core.domain.abstractions.transaction;

public interface ITransactionExecutor {
    void runInTransaction(Runnable runnable);
}
