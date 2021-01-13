package com.bilyoner.assignment.balanceapi.service;

import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.UserBalanceDto;
import com.bilyoner.assignment.balanceapi.model.enums.BalanceTransactionalType;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceHistoryEntity;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceHistoryRepository;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceHistoryRepository userBalanceHistoryRepository;
    private final ModelMapper modelMapper;


    public void updateBalance(UpdateBalanceRequest updateBalanceRequest) {
        /**
         * TODO : Implement update balance
         */
        UserBalanceEntity userBalanceEntity = userBalanceRepository.findByUserId(updateBalanceRequest.getUserId());
        createBalanceHistory(userBalanceEntity);
        userBalanceEntity.setAmount(updateBalanceRequest.getAmount());
        userBalanceEntity.setBalanceTransactionalType(BalanceTransactionalType.valueOf(updateBalanceRequest.getBalanceTransactionType()));
        userBalanceRepository.save(userBalanceEntity);
    }

    /**
     * Create balance history
     * @param userBalanceEntity
     */
    private void createBalanceHistory(UserBalanceEntity userBalanceEntity){
        UserBalanceHistoryEntity historyEntity = new UserBalanceHistoryEntity();
        historyEntity.setAmount(userBalanceEntity.getAmount());
        historyEntity.setBalanceTransactionalType(userBalanceEntity.getBalanceTransactionalType());
        historyEntity.setDate(LocalDateTime.now());
        historyEntity.setUserId(userBalanceEntity.getUserId());
        userBalanceHistoryRepository.save(historyEntity);
    }

    /**
     *
     * @param userId
     * @return
     */
    public UserBalanceDto getUserAmount(Long userId){
        return modelMapper.map(userBalanceRepository.findByUserId(userId), UserBalanceDto.class);
    }
}
