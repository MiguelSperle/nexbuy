package com.miguelsperle.nexbuy.core.application.ports.out.transaction;

public interface TransactionExecutor {
    void runTransaction(Runnable runnable);
    void registerAfterCommit(Runnable runnable);
}
