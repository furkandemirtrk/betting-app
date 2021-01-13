package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.exception.CouponException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.BalanceTransactionalType;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.bilyoner.assignment.couponapi.repository.CouponRepository;
import com.bilyoner.assignment.couponapi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;
    private final BalanceService balanceService;
    private final ModelMapper modelMapper;


    public List<CouponDTO> getAllCouponsByCouponStatus(CouponStatusEnum couponStatus) {
        log.info("getAllCouponsByCouponStatus start");
        return Arrays.asList(modelMapper.map(couponRepository.findAllByStatus(couponStatus), CouponDTO[].class));
    }

    public CouponDTO createCoupon(CouponCreateRequest couponCreateRequest) throws CouponException{

        /**
         * TODO : Implement create coupon logic
         */
        log.info("createCoupon start");
        CouponEntity couponEntity = new CouponEntity();
        if (couponCreateRequest != null && !couponCreateRequest.getEventIds().isEmpty()){
            List<EventEntity> eventEntityList = eventRepository.findAllById(couponCreateRequest.getEventIds());
            Integer maxMbs = eventEntityList.stream().sorted((o1, o2)->o2.getMbs(). compareTo(o1.getMbs())).limit(1).collect(Collectors.toList()).get(0).getMbs();
            if (maxMbs <= eventEntityList.size()){
                if (!footballAndTennisMismatch(eventEntityList)) {
                    if (checkMatchDate(eventEntityList)){
                        couponEntity.setCreateDate(LocalDateTime.now());
                        couponEntity.setUpdateDate(LocalDateTime.now());
                        couponEntity.setCost(BigDecimal.valueOf(5));
                        couponEntity.setStatus(CouponStatusEnum.CREATED);
                        Set<Long> eventIds = new HashSet<>();
                        eventEntityList.forEach(eventEntity -> eventIds.add(eventEntity.getId()) );
                        couponEntity.setEventIds(eventIds);
                        couponRepository.save(couponEntity);
                    } else {
                        throw new CouponException(ErrorCodeEnum.PAST_DATED_MATCHES);
                    }
                } else {
                    throw new CouponException(ErrorCodeEnum.FOOTBALL_VS_TENNIS_MISMATCH);
                }
            } else {
                throw new CouponException(ErrorCodeEnum.FIELD_VALIDATION_ERROR);
            }
        } else {
            throw new CouponException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR);
        }
        log.info("createCoupon finished");
        return modelMapper.map(couponEntity,CouponDTO.class);
    }

    public List<CouponDTO> playCoupons(CouponPlayRequest couponPlayRequest) throws CouponException {
        /**
         * TODO : Implement play coupons
         */
        log.info("playCoupons start");
        if (couponPlayRequest == null || couponPlayRequest.getCouponIds().isEmpty() || couponPlayRequest.getUserId() == null){
            throw new CouponException(ErrorCodeEnum.FIELD_VALIDATION_ERROR);
        }
        List<CouponEntity> couponEntityList = couponRepository.findAllById(couponPlayRequest.getCouponIds());
        List<CouponEntity> playedCoupons = couponEntityList.stream().filter(coupon -> coupon.getStatus().equals(CouponStatusEnum.PLAYED)).collect(Collectors.toList());
        if (!playedCoupons.isEmpty()){
            throw new CouponException(ErrorCodeEnum.FIELD_VALIDATION_ERROR);
        }

        double userAmount = balanceService.getUserAmount(couponPlayRequest.getUserId()).doubleValue();
        double sumCouponAmount = couponPlayRequest.getCouponIds().size() * 5;
        if (userAmount < sumCouponAmount){
            throw new CouponException(ErrorCodeEnum.INSUFFICIENT_BALANCE);
        }
        double newAmount = userAmount - sumCouponAmount;
        for (CouponEntity couponEntity : couponEntityList){
            couponEntity.setUserId(couponPlayRequest.getUserId());
            couponEntity.setStatus(CouponStatusEnum.PLAYED);
            couponEntity.setPlayDate(LocalDateTime.now());
        }
        couponRepository.saveAll(couponEntityList);
        balanceService.updateBalance(couponPlayRequest.getUserId(), BigDecimal.valueOf(newAmount), BalanceTransactionalType.BUY_COUPON);
        log.info("playCoupons finished");
        return Arrays.asList(modelMapper.map(couponEntityList, CouponDTO[].class)) ;
    }

    public CouponDTO cancelCoupon(Long couponId) throws CouponException{
        /**
         * TODO : Implement cancel coupon
         */
        if (couponId == null){
            throw new CouponException(ErrorCodeEnum.FIELD_VALIDATION_ERROR);
        }

        CouponEntity couponEntity = couponRepository.findById(couponId).get();
        if (couponEntity.getPlayDate().isAfter(LocalDateTime.now().minusMinutes(5))) {
            double userAmount = balanceService.getUserAmount(couponEntity.getUserId()).doubleValue();
            balanceService.updateBalance(couponEntity.getUserId(), BigDecimal.valueOf(userAmount + 5), BalanceTransactionalType.CANCEL_COUPON);
            couponEntity.setStatus(CouponStatusEnum.CREATED);
            couponEntity.setUserId(null);
            couponEntity.setPlayDate(null);
            couponRepository.save(couponEntity);
        } else {
            throw new CouponException(ErrorCodeEnum.COUPON_CANNOT_BE_CANCEL);
        }
        return modelMapper.map(couponEntity, CouponDTO.class);
    }

    public List<CouponDTO> getPlayedCoupons(Long userId) throws CouponException {
        /**
         * TODO : Implement get played coupons
         */
        if (userId == null){
            throw new CouponException(ErrorCodeEnum.FIELD_VALIDATION_ERROR);
        }
        List<CouponEntity> couponEntityList = couponRepository.findAllByStatusAndUserId(CouponStatusEnum.PLAYED, userId);
        return Arrays.asList(modelMapper.map(couponEntityList, CouponDTO[].class));
    }

    /**
     *
     * @param eventEntityList
     * @return
     */
    private boolean footballAndTennisMismatch(List<EventEntity> eventEntityList){
        List<EventEntity> footballEventList = eventEntityList.stream().filter(event -> event.getType().equals(EventTypeEnum.FOOTBALL)).collect(Collectors.toList());
        List<EventEntity> tennisEventList = eventEntityList.stream().filter(event -> event.getType().equals(EventTypeEnum.TENNIS)).collect(Collectors.toList());
        return !footballEventList.isEmpty() && !tennisEventList.isEmpty();
    }

    /**
     *
     * @param eventEntityList
     * @return
     */
    private boolean checkMatchDate(List<EventEntity> eventEntityList){
        List<EventEntity> pastDatedMatches = eventEntityList.stream().filter(eventEntity -> eventEntity.getEventDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        return pastDatedMatches.size() == eventEntityList.size();
    }
}
