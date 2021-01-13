package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.enums.BalanceTransactionalType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class BalanceService {


    private final static BalanceRestClient balanceRestClient = new BalanceRestClient();

    public void updateBalance(Long userId, BigDecimal newAmount, BalanceTransactionalType balanceTransactionalType) {
        /**
         * TODO : Implement update balance
         */
        balanceRestClient.updateBalance(userId, newAmount, balanceTransactionalType);
    }

    public BigDecimal getUserAmount(Long userId){
        return balanceRestClient.getUserAmount(userId);
    }
}
