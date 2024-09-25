package com.victorsantos.customer.transaction.application.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.victorsantos.customer.transaction.application.common.exception.UnprocessableEntityException;
import com.victorsantos.customer.transaction.application.exception.ExceptionFactory;
import com.victorsantos.customer.transaction.application.service.account.AccountService;
import com.victorsantos.customer.transaction.domain.entity.Transaction;
import com.victorsantos.customer.transaction.domain.enums.OperationType;
import com.victorsantos.customer.transaction.infra.data.TransactionRepository;
import com.victorsantos.customer.transaction.infra.data.model.TransactionModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TransactionServiceImpl.class})
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ExceptionFactory exceptionFactory;

    @Test
    @DisplayName("create: given transaction when valid then create")
    void testCreate_givenTransaction_whenValid_thenCreate() {
        var accountId = 1L;
        var operationType = OperationType.PURCHASE;
        var operationTypeId = operationType.getId();
        var amount = BigDecimal.valueOf(1000);
        var inputTransaction = Transaction.builder()
                .accountId(accountId)
                .operationType(operationType)
                .amount(amount)
                .build();

        var transactionId = 1L;
        var eventDate = LocalDateTime.now();

        when(accountService.existsById(accountId)).thenReturn(true);
        when(transactionRepository.save(any())).thenAnswer(invocation -> {
            TransactionModel model = invocation.getArgument(0);
            model.setId(transactionId);
            model.setEventDate(eventDate);
            return model;
        });

        var outputTransaction = transactionService.create(inputTransaction);
        var expectedOutputTransaction = Transaction.builder()
                .id(transactionId)
                .accountId(accountId)
                .operationType(operationType)
                .amount(amount)
                .eventDate(eventDate)
                .build();

        assertEquals(expectedOutputTransaction, outputTransaction);

        var savedModelArgCaptor = ArgumentCaptor.forClass(TransactionModel.class);
        verify(transactionRepository, times(1)).save(savedModelArgCaptor.capture());

        var savedModel = savedModelArgCaptor.getValue();
        assertEquals(amount, savedModel.getAmount());
        assertEquals(operationTypeId, savedModel.getOperationTypeId());
        assertEquals(accountId, savedModel.getAccountId());
        assertEquals(eventDate, savedModel.getEventDate());
    }

    @Test
    @DisplayName("create: given transaction when account does not exist then throw unprocessable transaction exception")
    void testCreate_givenTransaction_whenAccountDoesNotExist_thenThrowUnprocessableTransactionException() {
        var accountId = 1L;
        var inputTransaction = Transaction.builder()
                .accountId(accountId)
                .operationType(OperationType.PAYMENT)
                .amount(BigDecimal.valueOf(1000))
                .build();

        String detail = "Account not found with id " + accountId;
        var expectedException = new UnprocessableEntityException("Unprocessable transaction", detail);

        when(accountService.existsById(accountId)).thenReturn(false);
        when(exceptionFactory.unprocessableTransactionException(detail)).thenReturn(expectedException);

        var exception = assertThrows(expectedException.getClass(), () -> transactionService.create(inputTransaction));
        assertEquals(expectedException, exception);
    }
}
