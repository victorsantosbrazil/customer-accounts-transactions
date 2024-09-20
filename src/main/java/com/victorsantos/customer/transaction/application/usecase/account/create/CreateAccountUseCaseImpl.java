package com.victorsantos.customer.transaction.application.usecase.account.create;

import com.victorsantos.customer.transaction.application.service.account.AccountService;
import com.victorsantos.customer.transaction.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountService accountService;

    @Override
    public CreateAccountResponse run(CreateAccountRequest request) {
        Account account = new Account(request.getDocumentNumber());
        account = accountService.create(account);
        return new CreateAccountResponse(account.getId(), account.getDocumentNumber());
    }
}
