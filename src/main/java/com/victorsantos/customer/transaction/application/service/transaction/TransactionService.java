package com.victorsantos.customer.transaction.application.service.transaction;

import com.victorsantos.customer.transaction.domain.entity.Transaction;

public interface TransactionService {
    Transaction create(Transaction transaction);
}
