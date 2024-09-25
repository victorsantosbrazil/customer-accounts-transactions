package com.victorsantos.customer.transaction.application.common.exception;

public class ConflictException extends ApiException {

    public ConflictException(String title, String detail) {
        super("conflict_error", title, detail, 409);
    }
}
