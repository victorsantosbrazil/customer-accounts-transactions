package com.victorsantos.customer.transaction.application.usecase.transaction.create;

import com.victorsantos.customer.transaction.domain.enums.validation.OperationTypeId;
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
    @NotNull
    @Positive(message = "invalid account id")
    private Long accountId;

    @NotNull
    @OperationTypeId
    private Short operationTypeId;

    @NotNull
    @Positive(message = "must be a positive number")
    private BigDecimal amount;
}
