package com.victorsantos.customer.transaction.application.usecase.transaction.create;

import com.victorsantos.customer.transaction.domain.enums.validation.OperationTypeId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CreateTransactionRequest {
    @Schema(description = "Account ID", example = "1")
    @NotNull(message = "must not be null")
    @Positive(message = "invalid account id")
    private Long accountId;

    @Schema(
            description =
                    "Operation Type ID. Allowed values: 1 - PURCHASE, 2 - INSTALLMENT_PURCHASE, 3 - WITHDRAWAL, 4 - PAYMENT",
            example = "1")
    @NotNull(message = "must not be null")
    @OperationTypeId
    private Short operationTypeId;

    @Schema(description = "Transaction amount", example = "100.00")
    @NotNull(message = "must not be null")
    @Positive(message = "must be a positive number")
    private BigDecimal amount;
}
