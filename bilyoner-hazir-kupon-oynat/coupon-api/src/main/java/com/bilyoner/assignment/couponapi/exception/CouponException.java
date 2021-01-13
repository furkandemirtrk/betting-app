package com.bilyoner.assignment.couponapi.exception;

import lombok.Getter;


public class CouponException extends Exception{
  @Getter
  private final ErrorCodeEnum errorCodeEnum;

  public CouponException(ErrorCodeEnum errorCodeEnum){
    this.errorCodeEnum = errorCodeEnum;
  }

  public CouponException(String message, ErrorCodeEnum errorCodeEnum){
    super(message);
    this.errorCodeEnum = errorCodeEnum;
  }

  public CouponException(String message, Throwable cause, ErrorCodeEnum errorCodeEnum){
    super(message, cause);
    this.errorCodeEnum = errorCodeEnum;
  }

  public CouponException(Throwable cause, ErrorCodeEnum errorCodeEnum){
    super(cause);
    this.errorCodeEnum = errorCodeEnum;
  }

  public CouponException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCodeEnum errorCodeEnum){
    super(message, cause, enableSuppression, writableStackTrace);
    this.errorCodeEnum = errorCodeEnum;
  }
}
