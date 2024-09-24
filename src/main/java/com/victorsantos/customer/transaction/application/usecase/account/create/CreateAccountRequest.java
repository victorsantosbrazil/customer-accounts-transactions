package com.victorsantos.customer.transaction.application.usecase.account.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CreateAccountRequest {
    @Schema(description = "Document number", example = "12345678900")
    @NotBlank(message = "must not be blank")
    private String documentNumber;
}
