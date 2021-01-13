package com.bilyoner.assignment.couponapi.model;

import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO implements Serializable {

    private Long id;
    private Long userId;
    private CouponStatusEnum status;
    private BigDecimal cost;
    private Set<Long> eventIds;
    private LocalDateTime playDate;
}
