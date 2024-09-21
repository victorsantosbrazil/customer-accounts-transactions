package com.victorsantos.customer.transaction.application.common.exception;

import org.springframework.stereotype.Component;

@Component
public class CommonExceptionFactory {

    public NotFoundException notFoundByIdException(Object id) {
        String title = "Not found";
        String detail = "Not found by id " + id;
        return new NotFoundException(title, detail);
    }
}
