package com.victorsantos.customer.transaction.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private final String type;
    private final String title;
    private final String detail;
}
