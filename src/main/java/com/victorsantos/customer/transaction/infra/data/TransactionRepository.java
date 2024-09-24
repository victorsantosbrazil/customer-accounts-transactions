package com.victorsantos.customer.transaction.infra.data;

import com.victorsantos.customer.transaction.infra.data.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {}
