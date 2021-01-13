package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.enums.BalanceTransactionalType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Slf4j
public class BalanceService {


    private final static BalanceRestClient balanceRestClient = new BalanceRestClient();

    public void updateBalance(Long userId, BigDecimal newAmount, BalanceTransactionalType balanceTransactionalType) {
        /**
         * TODO : Implement update balance
         */
        log.info("BalanceRestClient updateBalance start");
        balanceRestClient.updateBalance(userId, newAmount, balanceTransactionalType);
        log.info("BalanceRestClient updateBalance finished");
    }

    public BigDecimal getUserAmount(Long userId){
        log.info("BalanceRestClient getUserAmount start");
        return balanceRestClient.getUserAmount(userId);
    }
}
