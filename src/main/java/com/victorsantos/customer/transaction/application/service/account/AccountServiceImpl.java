package com.victorsantos.customer.transaction.application.service.account;

import com.victorsantos.customer.transaction.application.exception.ExceptionFactory;
import com.victorsantos.customer.transaction.domain.entity.Account;
import com.victorsantos.customer.transaction.infra.data.AccountRepository;
import com.victorsantos.customer.transaction.infra.data.model.AccountModel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ExceptionFactory exceptionFactory;

    @Override
    public Account create(Account account) {
        var documentNumber = account.getDocumentNumber();
        if (accountRepository.existsByDocumentNumber(documentNumber)) {
            throw exceptionFactory.documentNumberAlreadyRegisteredException();
        }
        var model = new AccountModel(documentNumber);
        model = accountRepository.save(model);
        return mapEntity(model);
    }

    @Override
    public Optional<Account> getById(long id) {
        return accountRepository.findById(id).map(this::mapEntity);
    }

    @Override
    public boolean existsById(long id) {
        return accountRepository.existsById(id);
    }

    private Account mapEntity(AccountModel model) {
        return new Account(model.getId(), model.getDocumentNumber());
    }
}
