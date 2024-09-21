package com.victorsantos.customer.transaction.application.controller;

import static com.victorsantos.customer.transaction.application.common.doc.CommonSwaggerExamples.INTERNAL_SERVER_ERROR_REF;
import static com.victorsantos.customer.transaction.application.controller.ControllerPath.ACCOUNTS_PATH;

import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountRequest;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "accounts")
@RequestMapping(
        value = ACCOUNTS_PATH,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface AccountController {

    @Operation(
            summary = "Create a new account",
            responses = {
                @ApiResponse(responseCode = "201", description = "Account created"),
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
                                                                        "documentNumber": "must not be blank"
                                                                    }
                                                                }
                                                                """))),
                @ApiResponse(
                        responseCode = "409",
                        description = "Document number already registered",
                        content =
                                @Content(
                                        examples =
                                                @ExampleObject(
                                                        name = "Document number already registered",
                                                        value =
                                                                """
                                                                {
                                                                  "type": "conflict_error",
                                                                  "title": "Document number already registered",
                                                                  "detail": "Document number already registered"
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
    CreateAccountResponse create(@RequestBody @Valid CreateAccountRequest request);
}
