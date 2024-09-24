package com.victorsantos.customer.transaction.application.common.exception;

public class NotImplementedException extends ApiException {

    public NotImplementedException(String title, String detail) {
        super("not_implemented_error", title, detail, 501);
    }
}
