package com.victorsantos.customer.transaction.domain.enums.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OperationTypeIdValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationTypeId {
    String message() default "invalid operation type id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
