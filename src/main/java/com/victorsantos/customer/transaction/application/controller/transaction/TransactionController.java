package com.victorsantos.customer.transaction.application.controller.transaction;

import static com.victorsantos.customer.transaction.application.common.doc.CommonSwaggerExamples.INTERNAL_SERVER_ERROR_REF;
import static com.victorsantos.customer.transaction.application.controller.ControllerPath.TRANSACTIONS_PATH;

import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionRequest;
import com.victorsantos.customer.transaction.application.usecase.transaction.create.CreateTransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "transactions")
@RequestMapping(TRANSACTIONS_PATH)
public interface TransactionController {

    @Operation(
            summary = "Create a new transaction",
            responses = {
                @ApiResponse(responseCode = "201", description = "Transaction created"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Validation error",
                        content =
                                @Content(
                                        examples =
                                                @ExampleObject(
                                                        name = "Validation error",
                                                        value =
                                                                """
                                                                {
                                                                  "type": "validation_error",
                                                                  "title": "Validation error",
                                                                  "detail": "One or more fields are invalid",
                                                                  "errors": {
                                                                    "account_id": "invalid account id",
                                                                    "operation_type_id": "invalid operation type id",
                                                                    "amount": "must be a positive number"
                                                                  }
                                                                }
                                                                """))),
                @ApiResponse(
                        responseCode = "422",
                        description = "Unprocessable transaction",
                        content =
                                @Content(
                                        examples =
                                                @ExampleObject(
                                                        name = "Account not found",
                                                        value =
                                                                """
                                                                {
                                                                  "type": "unprocessable_entity_error",
                                                                  "title": "Unprocessable transaction",
                                                                  "detail": "Account not found with id 404"
                                                                }
                                                                """))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error",
                        content =
                                @Content(
                                        examples =
                                                @ExampleObject(
                                                        name = "Internal server error",
                                                        ref = INTERNAL_SERVER_ERROR_REF)))
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateTransactionResponse create(@RequestBody @Valid CreateTransactionRequest request);
}
