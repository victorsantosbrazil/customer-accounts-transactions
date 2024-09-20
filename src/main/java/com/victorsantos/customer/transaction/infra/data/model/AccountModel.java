package com.victorsantos.customer.transaction.infra.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String documentNumber;

    public AccountModel(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
