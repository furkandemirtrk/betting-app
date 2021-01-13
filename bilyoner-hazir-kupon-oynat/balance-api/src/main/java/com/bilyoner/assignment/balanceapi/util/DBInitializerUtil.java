package com.bilyoner.assignment.balanceapi.util;

import com.bilyoner.assignment.balanceapi.model.enums.BalanceTransactionalType;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DBInitializerUtil {

    private final UserBalanceRepository userBalanceRepository;

    /**
     * Populates sample balance information to user balance table.
     */
    @PostConstruct
    private void initDb() {

        if (userBalanceRepository.count() <= 0) {
            createUserBalances();
        }
    }

    private void createUserBalances() {
        UserBalanceEntity userBalanceEntity1 = UserBalanceEntity.builder()
                .userId(1L)
                .amount(BigDecimal.valueOf(10))
            .balanceTransactionalType(BalanceTransactionalType.ADD_MONEY)
                .build();

        UserBalanceEntity userBalanceEntity2 = UserBalanceEntity.builder()
                .userId(2L)
                .amount(BigDecimal.valueOf(20))
            .balanceTransactionalType(BalanceTransactionalType.ADD_MONEY)
                .build();

        UserBalanceEntity userBalanceEntity3 = UserBalanceEntity.builder()
                .userId(3L)
                .amount(BigDecimal.valueOf(30))
            .balanceTransactionalType(BalanceTransactionalType.ADD_MONEY)
                .build();

        UserBalanceEntity userBalanceEntity4 = UserBalanceEntity.builder()
                .userId(4L)
                .amount(BigDecimal.valueOf(40))
            .balanceTransactionalType(BalanceTransactionalType.ADD_MONEY)
                .build();

        userBalanceRepository.saveAll(Arrays.asList(userBalanceEntity1, userBalanceEntity2,
                userBalanceEntity3, userBalanceEntity4));
    }
}
