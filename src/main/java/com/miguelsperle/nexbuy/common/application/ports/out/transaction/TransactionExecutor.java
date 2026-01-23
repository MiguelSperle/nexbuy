package com.miguelsperle.nexbuy.common.application.ports.out.transaction;

public interface TransactionExecutor {
    void runTransaction(Runnable runnable);
    void registerAfterCommit(Runnable runnable);
}
