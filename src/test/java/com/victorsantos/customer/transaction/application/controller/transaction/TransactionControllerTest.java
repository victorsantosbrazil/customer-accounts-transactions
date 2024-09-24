package com.victorsantos.customer.transaction.application.controller.transaction;

import static com.victorsantos.customer.transaction.application.controller.ControllerPath.TRANSACTIONS_PATH;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsantos.customer.transaction.application.common.exception.CommonExceptionHandler;
import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionRequest;
import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionResponse;
import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionUseCase;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TransactionController.class)
@ContextConfiguration(classes = {TransactionControllerImpl.class, CommonExceptionHandler.class})
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private CommonExceptionHandler commonExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateTransactionUseCase createTransactionUseCase;

    @Test
    @DisplayName("create: given valid request when call endpoint then create transaction")
    void testCreate_givenValidRequest_whenCallEndpoint_thenCreateTransaction() throws Exception {
        var request = CreateTransactionRequest.builder()
                .accountId(1L)
                .operationTypeId((short) 1)
                .amount(BigDecimal.valueOf(100))
                .build();
        var requestJson = objectMapper.writeValueAsString(request);

        var response = CreateTransactionResponse.builder()
                .transactionId(1L)
                .accountId(request.getAccountId())
                .operationTypeId(request.getOperationTypeId())
                .amount(request.getAmount())
                .build();

        var responseJson = objectMapper.writeValueAsString(response);

        when(createTransactionUseCase.run(request)).thenReturn(response);

        mockMvc.perform(post(TRANSACTIONS_PATH).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));
    }

    @Test
    @DisplayName("create: given required fields missing when call endpoint then bad request")
    void testCreate_givenRequestWithRequiredFieldsMissing_whenCallEndpoint_thenBadRequest() throws Exception {
        var request = CreateTransactionRequest.builder().build();

        var requestJson = objectMapper.writeValueAsString(request);

        String[] invalidFields = {"account_id", "operation_type_id", "amount"};

        var resultActions = mockMvc.perform(
                        post(TRANSACTIONS_PATH).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        for (String field : invalidFields) {
            resultActions.andExpect(jsonPath("$.errors", hasKey(field)));
        }

        verify(createTransactionUseCase, never()).run(any());
    }

    @Test
    @DisplayName("create: given invalid fields when call endpoint then bad request")
    void testCreate_givenRequestWithInvalidFields_whenCallEndpoint_thenBadRequest() throws Exception {
        var request = CreateTransactionRequest.builder()
                .accountId(-1L)
                .operationTypeId((short) 0)
                .amount(BigDecimal.ZERO)
                .build();

        var requestJson = objectMapper.writeValueAsString(request);

        String[] invalidFields = {"account_id", "operation_type_id", "amount"};

        var resultActions = mockMvc.perform(
                        post(TRANSACTIONS_PATH).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        for (String field : invalidFields) {
            resultActions.andExpect(jsonPath("$.errors", hasKey(field)));
        }

        verify(createTransactionUseCase, never()).run(any());
    }
}
