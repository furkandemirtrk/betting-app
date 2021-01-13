package com.bilyoner.assignment.couponapi.util;

public class ApiPaths{
  private static final String BASE_PATH = "/api";

  public static final class EventController{
    public static final String CONTROLLER = BASE_PATH + "/event";
    public static final String ALL = "/all";
    public static final String CREATE_EVENT = "/createEvent";
  }

  public static final class CouponController{
    public static final String CONTROLLER = BASE_PATH + "/coupon";
    public static final String ALL_COUPONS_BY_COUPON_STATUS = "/allCouponsByCouponStatus";
    public static final String CREATE_COUPON = "/createCoupon";
    public static final String PLAYED_COUPON= "/playedCoupons";
    public static final String PLAY_COUPONS = "/playCoupons";
    public static final String CANCEL_COUPON = "/cancelCoupon";
  }

}
