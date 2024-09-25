package com.victorsantos.customer.transaction.application.usecase.account.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.victorsantos.customer.transaction.application.common.exception.NotFoundException;
import com.victorsantos.customer.transaction.application.exception.ExceptionFactory;
import com.victorsantos.customer.transaction.application.service.account.AccountService;
import com.victorsantos.customer.transaction.domain.entity.Account;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetAccountUseCaseImpl.class})
class GetAccountUseCaseTest {

    @Autowired
    private GetAccountUseCase useCase;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ExceptionFactory exceptionFactory;

    @Test
    @DisplayName("run: given id when account exists then return account")
    void givenId_whenAccountExists_thenReturnAccount() {
        var accountId = 1L;

        var account = new Account(accountId, "12345678900");
        when(accountService.getById(accountId)).thenReturn(Optional.of(account));

        var response = useCase.run(accountId);
        var expectedResponse = new GetAccountResponse(accountId, account.getDocumentNumber());

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("run: given id when account does not exist then throw not found exception")
    void givenId_whenAccountDoesNotExist_thenThrowNotFoundException() {
        var accountId = 1L;

        when(accountService.getById(accountId)).thenReturn(Optional.empty());

        var expectedException = new NotFoundException("Not found", "Not found");
        when(exceptionFactory.notFoundByIdException(accountId)).thenReturn(expectedException);

        var exception = assertThrows(expectedException.getClass(), () -> useCase.run(accountId));

        assertEquals(expectedException, exception);
    }
}
