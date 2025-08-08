package com.miguelsperle.nexbuy.shared.application.ports.out.transaction;

public interface TransactionExecutor {
    void runTransaction(Runnable runnable);
    void registerAfterCommit(Runnable runnable);
}
