package com.victorsantos.customer.transaction.application.controller;

import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountRequest;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountResponse;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountControllerImpl implements AccountController {

    private final CreateAccountUseCase createAccountUseCase;

    @Override
    public CreateAccountResponse create(CreateAccountRequest request) {
        return createAccountUseCase.run(request);
    }
}
