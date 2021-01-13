package com.bilyoner.assignment.balanceapi.persistence.entity;

import com.bilyoner.assignment.balanceapi.model.enums.BalanceTransactionalType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceHistoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime date;

    @Column
    private BigDecimal amount;

    @Column
    private Long userId;

    @Enumerated
    @Column(nullable = false)
    private BalanceTransactionalType balanceTransactionalType;

    /**
     * TODO : Implement missing parts
     */
}
