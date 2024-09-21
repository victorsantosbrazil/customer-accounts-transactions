package com.victorsantos.customer.transaction.application.service.account;

import com.victorsantos.customer.transaction.domain.entity.Account;
import java.util.Optional;

public interface AccountService {
    Account create(Account account);

    Optional<Account> getById(long accountId);
}
