package com.bilyoner.assignment.balanceapi.persistence.entity;

import com.bilyoner.assignment.balanceapi.model.enums.BalanceTransactionalType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceEntity {

    @Id
    @GeneratedValue
    private Long userId;

    @Column
    private BigDecimal amount;

    @Enumerated
    @Column(nullable = false)
    private BalanceTransactionalType balanceTransactionalType;

}
