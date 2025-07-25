package com.miguelsperle.nexbuy.core.infrastructure.adapters.out.transaction;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class TransactionExecutor implements ITransactionExecutor {
    private final PlatformTransactionManager platformTransactionManager;

    @Override
    public void runTransaction(Runnable runnable) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(this.platformTransactionManager);

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        transactionTemplate.setTimeout(5);

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            runnable.run();
        });
    }

    @Override
    public void registerAfterCommit(Runnable runnable) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            throw new IllegalStateException(
                    "Transaction synchronization is not active. Ensure this method is being called within an active transaction"
            );
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                runnable.run();
            }
        });
    }
}
