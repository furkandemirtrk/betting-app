package com.bilyoner.assignment.balanceapi.util;

public class ApiPaths{
  private static final String BASE_PATH = "/api";

  public static final class BalanceController{
    public static final String CONTROLLER = BASE_PATH + "/balance";
    public static final String GET_USER_AMOUNT = "/userAmount";
    public static final String UPDATE_BALANCE = "/updateBalance";
  }

}
