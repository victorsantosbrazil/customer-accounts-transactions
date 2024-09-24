package com.victorsantos.customer.transaction.application.usecase.transaction.create;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CreateTransactionResponse {
    @Schema(description = "Transaction ID", example = "1")
    private Long transactionId;

    @Schema(description = "Account ID", example = "1")
    private Long accountId;

    @Schema(
            description =
                    "Operation Type ID. Allowed values: 1 - PURCHASE, 2 - INSTALLMENT_PURCHASE, 3 - WITHDRAWAL, 4 - PAYMENT",
            example = "1")
    private Short operationTypeId;

    @Schema(description = "Transaction amount", example = "100.00")
    private BigDecimal amount;
}
