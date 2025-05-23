package com.miguelsperle.nexbuy.core.domain.abstractions.transaction;

public interface ITransactionExecutor {
    void runTransaction(Runnable runnable);
    void registerAfterCommit(Runnable runnable);
}
