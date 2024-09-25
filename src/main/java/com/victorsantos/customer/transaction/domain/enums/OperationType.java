package com.victorsantos.customer.transaction.domain.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    PURCHASE((short) 1),
    INSTALLMENT_PURCHASE((short) 2),
    WITHDRAWAL((short) 3),
    PAYMENT((short) 4);

    private final short id;

    OperationType(short id) {
        this.id = id;
    }

    public static OperationType fromId(short id) {
        for (OperationType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
