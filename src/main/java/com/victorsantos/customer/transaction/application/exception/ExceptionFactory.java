package com.victorsantos.customer.transaction.application.exception;

import com.victorsantos.customer.transaction.application.common.exception.CommonExceptionFactory;
import com.victorsantos.customer.transaction.application.common.exception.ConflictException;
import org.springframework.stereotype.Component;

@Component
public class ExceptionFactory extends CommonExceptionFactory {

    public ConflictException documentNumberAlreadyRegisteredException() {
        return new ConflictException("Document number already registered", "Document number already registered");
    }
}
