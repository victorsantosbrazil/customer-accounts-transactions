package com.victorsantos.customer.transaction.domain.entity;

import com.victorsantos.customer.transaction.domain.enums.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Transaction {
    private Long id;
    private Long accountId;
    private OperationType operationType;
    private BigDecimal amount;
    private LocalDateTime eventDate;
}
