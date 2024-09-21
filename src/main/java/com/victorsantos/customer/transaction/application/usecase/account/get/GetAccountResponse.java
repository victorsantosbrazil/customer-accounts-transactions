package com.victorsantos.customer.transaction.application.usecase.account.get;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetAccountResponse {

    @Schema(example = "1")
    private Long accountId;

    @Schema(example = "12345678900")
    private String documentNumber;
}
