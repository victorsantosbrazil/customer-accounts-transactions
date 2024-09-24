package com.victorsantos.customer.transaction.application.controller.account;

import static com.victorsantos.customer.transaction.application.controller.ControllerPath.ACCOUNTS_PATH;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsantos.customer.transaction.application.common.exception.CommonExceptionHandler;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountRequest;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountResponse;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountUseCase;
import com.victorsantos.customer.transaction.application.usecase.account.get.GetAccountResponse;
import com.victorsantos.customer.transaction.application.usecase.account.get.GetAccountUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = {AccountControllerImpl.class, CommonExceptionHandler.class})
class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @Autowired
    private CommonExceptionHandler commonExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAccountUseCase createAccountUseCase;

    @MockBean
    private GetAccountUseCase getAccountUseCase;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(commonExceptionHandler)
                .build();
    }

    @Test
    @DisplayName("create: given valid request when call endpoint then create account")
    void testCreate_givenValidRequest_whenCallEndpoint_thenCreateAccount() throws Exception {
        var request = new CreateAccountRequest("John Doe");
        var requestJson = objectMapper.writeValueAsString(request);

        var response = new CreateAccountResponse(1L, "1234567890");
        var responseJson = objectMapper.writeValueAsString(response);

        when(createAccountUseCase.run(request)).thenReturn(response);

        mockMvc.perform(post(ACCOUNTS_PATH).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));
    }

    @Test
    @DisplayName("create: given invalid request when call endpoint then bad request")
    void testCreate_givenInvalidRequest_whenCallEndpoint_thenBadRequest() throws Exception {
        var request = new CreateAccountRequest("");
        var requestJson = objectMapper.writeValueAsString(request);

        String[] invalidFields = {"documentNumber"};

        var resultActions = mockMvc.perform(
                        post(ACCOUNTS_PATH).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        for (String field : invalidFields) {
            resultActions.andExpect(jsonPath("$.errors", hasKey(field)));
        }

        verify(createAccountUseCase, never()).run(any());
    }

    @Test
    @DisplayName("get: given existing id when call endpoint then return account")
    void testGet_whenCallEndpoint_thenReturnAccount() throws Exception {
        var id = 1L;

        var response = new GetAccountResponse(1L, "1234567890");
        var responseJson = objectMapper.writeValueAsString(response);

        when(getAccountUseCase.run(id)).thenReturn(response);

        var uri = String.format("%s/%d", ACCOUNTS_PATH, id);

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }
}
