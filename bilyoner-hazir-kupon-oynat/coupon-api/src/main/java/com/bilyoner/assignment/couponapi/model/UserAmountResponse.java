package com.bilyoner.assignment.couponapi.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class UserAmountResponse{
  private Long userId;
  private BigDecimal amount;
}
