package com.victorsantos.customer.transaction.application.controller;

import static com.victorsantos.customer.transaction.application.controller.ControllerPath.ACCOUNTS_PATH;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountRequest;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountResponse;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = {AccountControllerImpl.class})
@ImportAutoConfiguration(JacksonAutoConfiguration.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAccountUseCase createAccountUseCase;

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

        mockMvc.perform(post(ACCOUNTS_PATH).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(createAccountUseCase, never()).run(any());
    }
}
