package com.victorsantos.customer.transaction.application.controller.account;

import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountRequest;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountResponse;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountUseCase;
import com.victorsantos.customer.transaction.application.usecase.account.get.GetAccountResponse;
import com.victorsantos.customer.transaction.application.usecase.account.get.GetAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountControllerImpl implements AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountUseCase getAccountUseCase;

    @Override
    public CreateAccountResponse create(CreateAccountRequest request) {
        return createAccountUseCase.run(request);
    }

    @Override
    public GetAccountResponse get(Long id) {
        return getAccountUseCase.run(id);
    }
}
