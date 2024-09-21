package com.victorsantos.customer.transaction.application.service.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.victorsantos.customer.transaction.application.common.exception.ConflictException;
import com.victorsantos.customer.transaction.application.exception.ExceptionFactory;
import com.victorsantos.customer.transaction.domain.entity.Account;
import com.victorsantos.customer.transaction.infra.data.AccountRepository;
import com.victorsantos.customer.transaction.infra.data.model.AccountModel;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AccountServiceImpl.class})
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ExceptionFactory exceptionFactory;

    @Test
    @DisplayName("create: given account when valid then create account")
    void testCreate_givenAccount_whenValid_thenCreateAccount() {
        var documentNumber = "12345678900";
        var request = new Account(documentNumber);
        var id = 1L;

        when(accountRepository.save(any())).thenAnswer(invocation -> {
            AccountModel input = invocation.getArgument(0);
            return new AccountModel(id, input.getDocumentNumber());
        });

        var response = accountService.create(request);
        var expectedResponse = new Account(id, documentNumber);

        assertEquals(expectedResponse, response);

        var savedModelArgCaptor = ArgumentCaptor.forClass(AccountModel.class);
        verify(accountRepository, times(1)).save(savedModelArgCaptor.capture());

        var savedModel = savedModelArgCaptor.getValue();
        assertEquals(documentNumber, savedModel.getDocumentNumber());
    }

    @Test
    @DisplayName("create: given account when document number already exists then throw exception")
    void testCreate_givenAccount_whenDocumentNumberAlreadyExists_thenThrowException() {
        var documentNumber = "12345678900";
        var account = new Account(documentNumber);

        when(accountRepository.existsByDocumentNumber(documentNumber)).thenReturn(true);

        var expectedException =
                new ConflictException("Document number already registered", "Document number already registered");
        when(exceptionFactory.documentNumberAlreadyRegisteredException()).thenReturn(expectedException);

        var exception = assertThrows(expectedException.getClass(), () -> accountService.create(account));
        assertEquals(expectedException, exception);

        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("getById: returns optional with account")
    void testGet_givenId_whenAccountExists_thenReturnAccount() {
        var id = 1L;
        var documentNumber = "12345678900";
        var account = new AccountModel(id, documentNumber);

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        var response = accountService.getById(id);
        var expected = Optional.of(new Account(id, documentNumber));

        assertEquals(expected, response);
    }
}
