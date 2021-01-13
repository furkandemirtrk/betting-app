package com.bilyoner.assignment.couponapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCodeEnum {

    INTERNAL_SERVER_ERROR(1000, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
    FIELD_VALIDATION_ERROR(1001, "Field validation error.", HttpStatus.BAD_REQUEST),
    CONTENT_NOT_FOUND_ERROR(1002, "Content not found.", HttpStatus.BAD_REQUEST),
    FOOTBALL_VS_TENNIS_MISMATCH(1003, "Football and tennis cannot be chosen together.", HttpStatus.BAD_REQUEST),
    PAST_DATED_MATCHES(1004, "Your voucher cannot contain past matches.", HttpStatus.BAD_REQUEST),
    COUPON_CANNOT_BE_CANCEL(1005, "Cannot be canceled because the coupon has expired.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE(1006, "You cannot play the game because your balance is insufficient.", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
