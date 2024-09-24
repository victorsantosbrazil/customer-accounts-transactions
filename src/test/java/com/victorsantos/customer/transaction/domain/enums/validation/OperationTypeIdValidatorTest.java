package com.victorsantos.customer.transaction.domain.enums.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.victorsantos.customer.transaction.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class OperationTypeIdValidatorTest {

    private final OperationTypeIdValidator validator = new OperationTypeIdValidator();

    @Test
    void givenId_whenNull_thenReturnTrue() {
        var result = validator.isValid(null, null);
        assertTrue(result);
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void givenId_whenValid_thenReturnTrue(OperationType type) {
        var result = validator.isValid(type.getId(), null);
        assertTrue(result);
    }

    @Test
    void givenId_whenInvalid_thenReturnFalse() {
        var result = validator.isValid((short) 0, null);
        assertFalse(result);
    }
}
