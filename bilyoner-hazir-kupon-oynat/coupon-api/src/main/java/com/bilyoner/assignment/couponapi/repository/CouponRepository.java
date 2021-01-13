package com.bilyoner.assignment.couponapi.repository;

import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
  Optional<CouponEntity> findById(Long id);
  List<CouponEntity> findAllByStatus(CouponStatusEnum couponStatusEnum);
  List<CouponEntity> findAllByStatusAndUserId(CouponStatusEnum couponStatusEnum, Long userId);
}
