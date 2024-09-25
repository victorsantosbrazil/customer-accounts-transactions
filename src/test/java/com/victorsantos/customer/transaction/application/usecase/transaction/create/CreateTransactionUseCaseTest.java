package com.victorsantos.customer.transaction.application.usecase.transaction.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.victorsantos.customer.transaction.application.exception.ExceptionFactory;
import com.victorsantos.customer.transaction.application.service.account.AccountService;
import com.victorsantos.customer.transaction.application.service.transaction.TransactionService;
import com.victorsantos.customer.transaction.domain.entity.Transaction;
import com.victorsantos.customer.transaction.domain.enums.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CreateTransactionUseCaseImpl.class})
class CreateTransactionUseCaseTest {

    @Autowired
    private CreateTransactionUseCase useCase;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ExceptionFactory exceptionFactory;

    @Test
    @DisplayName(
            "run: given a valid request when operation type is payment then create transaction with positive amount")
    void givenRequest_whenOperationTypeIsPayment_thenCreateTransactionWithPositiveAmount() {
        var accountId = 1L;
        var operationType = OperationType.PAYMENT;
        var operationTypeId = operationType.getId();
        var amount = BigDecimal.valueOf(1000);
        var request = new CreateTransactionRequest(accountId, operationTypeId, amount);

        var transaction = Transaction.builder()
                .accountId(accountId)
                .operationType(operationType)
                .amount(amount)
                .build();

        var transactionId = 1L;

        var transactionAfterCreate = Transaction.builder()
                .id(transactionId)
                .accountId(accountId)
                .operationType(operationType)
                .amount(amount)
                .eventDate(LocalDateTime.now())
                .build();

        var expectedResponse = CreateTransactionResponse.builder()
                .transactionId(transactionId)
                .accountId(accountId)
                .operationTypeId(operationTypeId)
                .amount(amount)
                .build();

        when(transactionService.create(transaction)).thenReturn(transactionAfterCreate);

        var response = useCase.run(request);

        assertEquals(expectedResponse, response);

        verify(transactionService, times(1)).create(transaction);
    }

    @ParameterizedTest
    @EnumSource(
            value = OperationType.class,
            names = {"PURCHASE", "INSTALLMENT_PURCHASE", "WITHDRAWAL"})
    @DisplayName(
            "run: given a valid request when operation type is not payment then create transaction with negative amount")
    void givenRequest_whenOperationTypeIsNotPayment_thenCreateTransactionWithNegativeAmount(
            OperationType operationType) {
        var accountId = 1L;
        var operationTypeId = operationType.getId();
        var amount = BigDecimal.valueOf(1000);
        var request = new CreateTransactionRequest(accountId, operationTypeId, amount);

        var transaction = Transaction.builder()
                .accountId(accountId)
                .operationType(operationType)
                .amount(amount.negate())
                .build();

        var transactionId = 1L;

        var transactionAfterCreate = Transaction.builder()
                .id(transactionId)
                .accountId(accountId)
                .operationType(operationType)
                .amount(transaction.getAmount())
                .eventDate(LocalDateTime.now())
                .build();

        when(transactionService.create(transaction)).thenReturn(transactionAfterCreate);

        var response = useCase.run(request);
        var expectedResponse = CreateTransactionResponse.builder()
                .transactionId(transactionId)
                .accountId(accountId)
                .operationTypeId(operationTypeId)
                .amount(amount)
                .build();

        assertEquals(expectedResponse, response);

        verify(transactionService, times(1)).create(transaction);
    }
}
