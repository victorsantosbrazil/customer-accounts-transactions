package com.victorsantos.customer.transaction.application.usecase.account.get;

import com.victorsantos.customer.transaction.application.exception.ExceptionFactory;
import com.victorsantos.customer.transaction.application.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GetAccountUseCaseImpl implements GetAccountUseCase {

    private final AccountService accountService;
    private final ExceptionFactory exceptionFactory;

    @Override
    public GetAccountResponse run(Long id) {
        var account = accountService.getById(id).orElseThrow(() -> exceptionFactory.notFoundByIdException(id));
        return new GetAccountResponse(account.getId(), account.getDocumentNumber());
    }
}
