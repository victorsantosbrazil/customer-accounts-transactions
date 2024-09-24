package com.victorsantos.customer.transaction.application.service.transaction;

import com.victorsantos.customer.transaction.application.exception.ExceptionFactory;
import com.victorsantos.customer.transaction.application.service.account.AccountService;
import com.victorsantos.customer.transaction.domain.entity.Transaction;
import com.victorsantos.customer.transaction.domain.enums.OperationType;
import com.victorsantos.customer.transaction.infra.data.TransactionRepository;
import com.victorsantos.customer.transaction.infra.data.model.TransactionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ExceptionFactory exceptionFactory;

    @Override
    public Transaction create(Transaction transaction) {
        var accountId = transaction.getAccountId();
        if (!accountService.existsById(accountId)) {
            throw exceptionFactory.unprocessableTransactionException("Account not found with id " + accountId);
        }
        var model = mapModel(transaction);
        model = transactionRepository.save(model);
        return mapEntity(model);
    }

    private TransactionModel mapModel(Transaction entity) {
        return TransactionModel.builder()
                .accountId(entity.getAccountId())
                .operationTypeId(entity.getOperationType().getId())
                .amount(entity.getAmount())
                .build();
    }

    private Transaction mapEntity(TransactionModel model) {
        return Transaction.builder()
                .id(model.getId())
                .accountId(model.getAccountId())
                .operationType(OperationType.fromId(model.getOperationTypeId()))
                .amount(model.getAmount())
                .eventDate(model.getEventDate())
                .build();
    }
}
