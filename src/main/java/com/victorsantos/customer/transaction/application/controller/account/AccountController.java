package com.victorsantos.customer.transaction.application.controller.account;

import static com.victorsantos.customer.transaction.application.common.doc.CommonSwaggerExamples.*;
import static com.victorsantos.customer.transaction.application.controller.ControllerPath.ACCOUNTS_PATH;

import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountRequest;
import com.victorsantos.customer.transaction.application.usecase.account.create.CreateAccountResponse;
import com.victorsantos.customer.transaction.application.usecase.account.get.GetAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "accounts")
@RequestMapping(value = ACCOUNTS_PATH)
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

    @Operation(
            summary = "Get account by id",
            responses = {
                @ApiResponse(responseCode = "200", description = "Account found"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid id",
                        content = @Content(examples = @ExampleObject(name = "Invalid id", ref = INVALID_ID_PARAM_REF))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Account not found",
                        content = @Content(examples = @ExampleObject(name = "Not found", ref = NOT_FOUND_BY_ID_REF))),
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
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    GetAccountResponse get(@PathVariable Long id);
}
