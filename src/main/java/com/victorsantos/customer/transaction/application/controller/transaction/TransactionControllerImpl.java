package com.victorsantos.customer.transaction.application.controller.transaction;

import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionRequest;
import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionResponse;
import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class TransactionControllerImpl implements TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    @Override
    public CreateTransactionResponse create(CreateTransactionRequest request) {
        return createTransactionUseCase.run(request);
    }
}
