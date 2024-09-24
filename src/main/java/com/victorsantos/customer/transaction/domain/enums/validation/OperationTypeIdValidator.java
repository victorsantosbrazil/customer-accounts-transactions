package com.victorsantos.customer.transaction.domain.enums.validation;

import static java.util.Objects.isNull;

import com.victorsantos.customer.transaction.domain.enums.OperationType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OperationTypeIdValidator implements ConstraintValidator<OperationTypeId, Short> {
    @Override
    public boolean isValid(Short value, ConstraintValidatorContext context) {
        if (isNull(value)) {
            return true;
        }
        return isInEnum(value);
    }

    private static boolean isInEnum(Short value) {
        try {
            OperationType.fromId(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
