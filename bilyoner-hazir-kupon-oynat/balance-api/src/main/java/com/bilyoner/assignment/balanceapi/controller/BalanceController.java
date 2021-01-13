package com.bilyoner.assignment.balanceapi.controller;

import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.UserBalanceDto;
import com.bilyoner.assignment.balanceapi.service.BalanceService;
import com.bilyoner.assignment.balanceapi.util.ApiPaths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = ApiPaths.BalanceController.CONTROLLER)
@Api(value = ApiPaths.BalanceController.CONTROLLER, tags = { "Balance APIs" })
@RequiredArgsConstructor
@Slf4j
public class BalanceController {

    private final BalanceService balanceService;

    @ApiOperation(value = "Update balance")
    @PutMapping(value = ApiPaths.BalanceController.UPDATE_BALANCE)
    public void updateBalance(@Valid @RequestBody UpdateBalanceRequest updateBalanceRequest) {
        balanceService.updateBalance(updateBalanceRequest);
    }

    @ApiOperation(value = "Get user amount by user id parameter")
    @GetMapping(value = ApiPaths.BalanceController.GET_USER_AMOUNT + "/{userId}")
    public UserBalanceDto getUserAmount(@PathVariable Long userId){
        return balanceService.getUserAmount(userId);
    }
}
