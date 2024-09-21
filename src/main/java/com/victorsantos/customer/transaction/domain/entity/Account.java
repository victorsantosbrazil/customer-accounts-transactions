package com.victorsantos.customer.transaction.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Account {
    private Long id;
    private String documentNumber;

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
