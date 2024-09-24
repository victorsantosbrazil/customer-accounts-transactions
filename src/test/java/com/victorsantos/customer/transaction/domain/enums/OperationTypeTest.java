package com.victorsantos.customer.transaction.domain.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OperationTypeTest {

    @ParameterizedTest
    @MethodSource("provideIdsAndExpectedTypes")
    @DisplayName("fromId: given id when valid then returns correct operation type")
    void testFromId_givenId_thenReturnsCorrectOperationType(short id, OperationType expected) {
        OperationType result = OperationType.fromId(id);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideIdsAndExpectedTypes() {
        return Stream.of(
                Arguments.of((short) 1, OperationType.PURCHASE),
                Arguments.of((short) 2, OperationType.INSTALLMENT_PURCHASE),
                Arguments.of((short) 3, OperationType.WITHDRAWAL),
                Arguments.of((short) 4, OperationType.PAYMENT));
    }

    @Test
    @DisplayName("fromId: given invalid id when invalid then throws IllegalArgumentException")
    void testFromId_givenId_whenInvalid_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> OperationType.fromId((short) 0));
    }
}
