package com.victorsantos.customer.transaction.application.common.exception;

public class NotFoundException extends ApiException {

    public NotFoundException(String title, String detail) {
        super("not_found_error", title, detail, 404);
    }
}
