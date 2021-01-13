package com.bilyoner.assignment.balanceapi.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceDto{
  private Long userId;
  private BigDecimal amount;
}
