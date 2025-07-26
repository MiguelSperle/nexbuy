package com.miguelsperle.nexbuy.core.application.ports.out.transaction;

public interface ITransactionExecutor {
    void runTransaction(Runnable runnable);
    void registerAfterCommit(Runnable runnable);
}
