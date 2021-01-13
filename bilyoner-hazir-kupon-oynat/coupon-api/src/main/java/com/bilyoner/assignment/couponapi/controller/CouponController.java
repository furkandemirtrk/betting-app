package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.exception.CouponException;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.service.BalanceService;
import com.bilyoner.assignment.couponapi.service.CouponService;
import com.bilyoner.assignment.couponapi.util.ApiPaths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping(ApiPaths.CouponController.CONTROLLER)
@RequiredArgsConstructor
@Api(value = ApiPaths.CouponController.CONTROLLER, tags = { "Coupon APIs" })
@Slf4j
public class CouponController {

    private final CouponService couponService;
    private final BalanceService  balanceService;

    /**
     * TODO : Implement missing parts
     */


    @ApiOperation(value = "Get all coupons by coupon status", response = CouponDTO.class)
    @PostMapping(value = ApiPaths.CouponController.ALL_COUPONS_BY_COUPON_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CouponDTO> getAllCouponsByCouponStatus(@RequestParam CouponStatusEnum couponStatus) {
        return couponService.getAllCouponsByCouponStatus(couponStatus);
    }

    @ApiOperation(value = "Create coupon", response = CouponDTO.class)
    @PostMapping(value = ApiPaths.CouponController.CREATE_COUPON, produces = MediaType.APPLICATION_JSON_VALUE)
    public CouponDTO createCoupon(@RequestBody @Valid CouponCreateRequest couponCreateRequest) throws CouponException{
        log.info("CouponController createCoupon start");
        return couponService.createCoupon(couponCreateRequest);
    }

    @ApiOperation(value = "Get played coupon by userId", response = CouponDTO.class)
    @GetMapping(value = ApiPaths.CouponController.PLAYED_COUPON + "/{userId}")
    public List<CouponDTO> getPlayedCoupons(@PathVariable Long userId)  throws CouponException {
        return couponService.getPlayedCoupons(userId);
    }

    @ApiOperation(value = "Play coupons", response = CouponDTO.class)
    @PostMapping(value = ApiPaths.CouponController.PLAY_COUPONS , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CouponDTO> playCoupons(@Valid @RequestBody CouponPlayRequest couponPlayRequest) throws CouponException {
        return couponService.playCoupons(couponPlayRequest);
    }

    @ApiOperation(value = "Cancel coupon", response = CouponDTO.class)
    @GetMapping(value = ApiPaths.CouponController.CANCEL_COUPON + "/{couponId}")
    public CouponDTO cancelCoupon(@PathVariable Long couponId) throws CouponException {
        return couponService.cancelCoupon(couponId);
    }

}
