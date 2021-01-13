package com.bilyoner.assignment.balanceapi.model;

import lombok.*;

import java.util.Date;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer code;
    private String message;
    private Date date;
}
