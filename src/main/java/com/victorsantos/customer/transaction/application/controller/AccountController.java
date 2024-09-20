package com.victorsantos.customer.transaction.application.controller;

import static com.victorsantos.customer.transaction.application.controller.ControllerPath.ACCOUNTS_PATH;

import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountRequest;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Accounts")
@RequestMapping(ACCOUNTS_PATH)
public interface AccountController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateAccountResponse create(@Valid @RequestBody CreateAccountRequest request);
}
