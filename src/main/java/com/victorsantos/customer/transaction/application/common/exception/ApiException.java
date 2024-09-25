package com.victorsantos.customer.transaction.application.common.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ApiException extends RuntimeException {
    private final String type;
    private final String title;
    private final String detail;
    private final int statusCode;

    public ApiException(String type, String title, String detail, int statusCode) {
        super(detail);
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.statusCode = statusCode;
    }
}
