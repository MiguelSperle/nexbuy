package com.miguelsperle.nexbuy.core.infrastructure.transaction;

import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class TransactionExecutor implements ITransactionExecutor {
    private final PlatformTransactionManager platformTransactionManager;

    @Override
    public void runInTransaction(Runnable runnable) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        transactionTemplate.setTimeout(30);

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            runnable.run();
        });
    }
}
