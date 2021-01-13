package com.bilyoner.assignment.couponapi.model;

import lombok.*;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer code;
    private String message;
    private Date date;
}
