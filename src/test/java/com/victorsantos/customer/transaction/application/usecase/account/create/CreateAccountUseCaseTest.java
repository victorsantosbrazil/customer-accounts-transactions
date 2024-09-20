package com.victorsantos.customer.transaction.application.usecase.account.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.victorsantos.customer.transaction.application.service.account.AccountService;
import com.victorsantos.customer.transaction.domain.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CreateAccountUseCaseImpl.class})
class CreateAccountUseCaseTest {

    @Autowired
    private CreateAccountUseCase createAccountUseCase;

    @MockBean
    private AccountService accountService;

    @Test
    @DisplayName("run: given request, when valid, then create account")
    void givenRequest_whenValid_thenCreateAccount() {
        var documentNumber = "12345678900";
        var request = new CreateAccountRequest(documentNumber);

        var accountId = 1L;
        var expectedAccountInput = new Account(documentNumber);
        var expectedAccountOutput = new Account(accountId, documentNumber);

        when(accountService.create(expectedAccountInput)).thenReturn(expectedAccountOutput);

        var response = createAccountUseCase.run(request);
        var expectedResponse = new CreateAccountResponse(accountId, documentNumber);

        assertEquals(expectedResponse, response);

        verify(accountService, times(1)).create(expectedAccountInput);
    }
}
