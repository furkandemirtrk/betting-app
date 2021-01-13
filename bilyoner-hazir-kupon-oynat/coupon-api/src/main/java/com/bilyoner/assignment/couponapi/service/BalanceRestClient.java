package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.exception.CouponException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.couponapi.model.UserAmountRequest;
import com.bilyoner.assignment.couponapi.model.UserAmountResponse;
import com.bilyoner.assignment.couponapi.model.enums.BalanceTransactionalType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class BalanceRestClient{

  private static final String BASE_URL = "http://localhost:9090/api";
  private static final String GET_USER_AMOUNT_URL = BASE_URL + "/balance/userAmount/{userId}";
  private static final String UPDATE_BALANCE_URL = BASE_URL + "/balance/updateBalance";
  private ObjectMapper objectMapper = new ObjectMapper();

  public BalanceRestClient(){
  }

  public BigDecimal getUserAmount(Long userId){
    log.info("BalanceRestClient getUserAmount start");
    UserAmountResponse userAmountResponse;
    try{
      Map<String, String> params = new HashMap<>();
      params.put("userId", String.valueOf(userId));

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(org.springframework.http.MediaType.valueOf("application/json;charset=UTF-8"));

      HttpEntity<String> request = new HttpEntity<>(null, headers);
      ResponseEntity<Object> objectResponse = doRequest(GET_USER_AMOUNT_URL,params, HttpMethod.GET, request);

      if (objectResponse.getStatusCode() != HttpStatus.OK){
        throw new CouponException(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
      } else {
        log.info("BalanceRestClient getUserAmount ok");
        userAmountResponse = objectMapper.convertValue(objectResponse.getBody(), UserAmountResponse.class);
        return userAmountResponse.getAmount();
      }
    }
    catch (Exception e){
      log.error("getUserAmount ", e);
    }
    return null;
  }

  /**
   *
   * @param userId
   * @param newAmount
   * @return
   */
  public boolean updateBalance(Long userId, BigDecimal newAmount, BalanceTransactionalType balanceTransactionalType){
    log.info("BalanceRestClient updateBalance start");
    try{
      UpdateBalanceRequest updateBalanceRequest = new UpdateBalanceRequest(userId, newAmount, "", balanceTransactionalType.toString());
      String requestBody = objectMapper.writeValueAsString(updateBalanceRequest);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(org.springframework.http.MediaType.valueOf("application/json;charset=UTF-8"));

      HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
      ResponseEntity<Object> objectResponse = doRequest(UPDATE_BALANCE_URL, HttpMethod.PUT, request);

      if (objectResponse.getStatusCode() != HttpStatus.OK){
        throw new CouponException(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
      } else {
        log.info("update balance ok");
        return true;
      }
    }
    catch (Exception e){
      log.error("updateBalance error ",e);
    }
    return false;
  }

  /**
   * doRequest with parameters
   *
   * @param requestUrl
   * @param params
   * @param requestMethod
   * @param request
   * @return
   */
  private ResponseEntity<Object> doRequest(String requestUrl, Map<String, String> params, HttpMethod requestMethod, HttpEntity<String> request){
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(requestUrl);
    String url = builder.buildAndExpand(params).toUri().toString();
    return doRequest(url, requestMethod, request);
  }


  /**
   * send request
   *
   * @param requestUrl
   * @param requestMethod
   * @param request
   * @return
   */
  private ResponseEntity<Object> doRequest(String requestUrl, HttpMethod requestMethod, HttpEntity<String> request){
    log.debug("doRequest started. url:[{}]", requestUrl);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Object> response = restTemplate.exchange(requestUrl, requestMethod, request, Object.class);
    return response;
  }
}
