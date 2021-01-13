package com.bilyoner.assignment.couponapi.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBalanceRequest{
  private Long userId;
  private BigDecimal amount;
  private String transactionId;
  private String balanceTransactionType;
}
