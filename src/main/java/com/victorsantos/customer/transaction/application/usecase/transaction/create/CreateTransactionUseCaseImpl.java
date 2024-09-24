package com.victorsantos.customer.transaction.application.usecase.transaction.create;

import com.victorsantos.customer.transaction.application.service.transaction.TransactionService;
import com.victorsantos.customer.transaction.domain.entity.Transaction;
import com.victorsantos.customer.transaction.domain.enums.OperationType;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {

    private final TransactionService transactionService;

    @Override
    public CreateTransactionResponse run(CreateTransactionRequest request) {
        var transaction = toEntity(request);
        transaction = transactionService.create(transaction);
        return toResponse(transaction);
    }

    private Transaction toEntity(CreateTransactionRequest request) {
        var operationType = OperationType.fromId(request.getOperationTypeId());
        return Transaction.builder()
                .accountId(request.getAccountId())
                .operationType(operationType)
                .amount(toOperationAmount(operationType, request.getAmount()))
                .build();
    }

    private BigDecimal toOperationAmount(OperationType operationType, BigDecimal amount) {
        return switch (operationType) {
            case PURCHASE, INSTALLMENT_PURCHASE, WITHDRAWAL:
                yield amount.negate();
            default:
                yield amount;
        };
    }

    private CreateTransactionResponse toResponse(Transaction transaction) {
        return CreateTransactionResponse.builder()
                .transactionId(transaction.getId())
                .accountId(transaction.getAccountId())
                .operationTypeId(transaction.getOperationType().getId())
                .amount(transaction.getAmount().abs())
                .build();
    }
}
