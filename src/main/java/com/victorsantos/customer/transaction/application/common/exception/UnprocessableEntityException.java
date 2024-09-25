package com.victorsantos.customer.transaction.application.common.exception;

public class UnprocessableEntityException extends ApiException {

    public UnprocessableEntityException(String title, String detail) {
        super("unprocessable_entity_error", title, detail, 422);
    }
}
