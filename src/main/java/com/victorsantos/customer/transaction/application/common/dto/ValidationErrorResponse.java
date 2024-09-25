package com.victorsantos.customer.transaction.application.common.dto;

import java.util.Map;
import lombok.*;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends ErrorResponse {
    private final Map<String, String> errors;

    @Builder
    public ValidationErrorResponse(String type, String title, String detail, Map<String, String> errors) {
        super(type, title, detail);
        this.errors = errors;
    }
}
