package com.victorsantos.customer.transaction.infra.data;

import com.victorsantos.customer.transaction.infra.data.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    boolean existsByDocumentNumber(String documentNumber);
}
